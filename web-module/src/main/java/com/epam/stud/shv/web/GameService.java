package com.epam.stud.shv.web;

import com.epam.stud.shv.datalayer.dao.DaoFactory;
import com.epam.stud.shv.datalayer.dao.GameDao;

import com.epam.stud.shv.datalayer.datalayer.entities.Game;
import com.epam.stud.shv.datalayer.datalayer.entities.User;
import com.epam.stud.shv.enums.GameConstants;
import com.epam.stud.shv.enums.GameState;
import com.epam.stud.shv.json.GameStep;
import com.sun.jersey.api.view.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/games")
public class GameService {

    private DaoFactory daoFactory = DaoFactory.getFactory();

    private Random rand = new Random();


    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Game> getGameList() {
        GameDao gameDao = daoFactory.getGameDao();
        return gameDao.getGameList();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response showLoginForm() {
        return Response.ok(new Viewable("/Login")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(User user, @CookieParam("JSESSIONID") String sessionId, @Context UriInfo uriInfo) {
        GameDao gameDao = daoFactory.getGameDao();
        user.setSessionId(sessionId);
        int id = gameDao.createGame(user);

        /*
        if(id != 0){
            return Response.ok(new Viewable("/Main")).build();
        }

        return Response.noContent().build();
        */
        if (id != 0) {
            Game game = gameDao.getGameById(id);
            URI uri = uriInfo.getBaseUriBuilder().path("/games/game/" + String.valueOf(id)).build();
            //return Response.seeOther(uri).build();
            return Response.status(201).entity(uri.toString()).build();
        }


        return Response.noContent().build();
    }

    @GET
    @Path("/game/{gameId}")
    @Produces(MediaType.TEXT_HTML)
    public Response showGame(@PathParam("gameId") int gameId, @CookieParam("JSESSIONID") String sessionId) {
        GameDao gameDao = daoFactory.getGameDao();
        Game game = gameDao.getGameById(gameId);

        if (game != null) {
            //мало кто ещё решит полезть 3-им в игру
            if (game.getCreator() != null && game.getEnemy() != null) {
                if(game.getCreator().getSessionId().equals(sessionId) || game.getEnemy().getSessionId().equals(sessionId)){
                    return Response.ok(new Viewable("/Main")).build();
                } else{
                    return Response.ok(new Viewable("/Login")).build();   //TODO редирект
                }

            }
        }

        return Response.ok(new Viewable("/Main")).build();

    }

    @POST
    @Path("/game/{gameId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response chooseGame(@PathParam("gameId") int gameId, User user, @CookieParam("JSESSIONID") String sessionId, @Context UriInfo uriInfo) {
        GameDao gameDao = daoFactory.getGameDao();
        Game game = gameDao.getGameById(gameId);
        user.setSessionId(sessionId);

        if (game != null) {
            if (game.getCreator() != null && game.getEnemy() == null) {
                game.setEnemy(user);

                //return Response.ok(new Viewable("/Main")).build();
                URI uri = uriInfo.getBaseUriBuilder().path("/games/game/" + String.valueOf(game.getId())).build();
                //return Response.seeOther(uri).build();
                return Response.status(201).entity(uri.toString()).build();
            }
        }

        return Response.noContent().build();
    }

    @DELETE
    @Path("/game/{gameId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam("gameId") int gameId, @Context UriInfo uriInfo){
        GameDao gameDao = daoFactory.getGameDao();
        gameDao.deleteGame(gameId);

        URI uri = uriInfo.getBaseUriBuilder().path("/games").build();
        return Response.status(201).entity(uri.toString()).build();
    }


    @GET
    @Path("/game/{gameId}/wait")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameStep waitEnemyBeforeStart(@PathParam("gameId") int gameId, @CookieParam("JSESSIONID") String sessionId) {
        GameDao gameDao = daoFactory.getGameDao();
        Game game = gameDao.getGameById(gameId);
        GameStep step = null;
        if (game != null) {
            if (game.getCreator() != null && game.getEnemy() != null) {
                int gameState;

                if (game.getCreator().getSide() == 0 && game.getEnemy().getSide() == 0) {
                    // подключены все, опрос впервые

                    int max = GameConstants.CROSS_PLAYER.getNumber();
                    int min = GameConstants.ZERO_PLAYER.getNumber();
                    int randomNum = getRandomNumber(max, min);

                    if (randomNum == max) {
                        game.getCreator().setSide(GameConstants.CROSS_PLAYER.getNumber());
                        game.getEnemy().setSide(GameConstants.ZERO_PLAYER.getNumber());
                    } else {
                        game.getEnemy().setSide(GameConstants.CROSS_PLAYER.getNumber());
                        game.getCreator().setSide(GameConstants.ZERO_PLAYER.getNumber());
                    }

                    game.setState(GameState.NOT_STARTED.getState());

                } else {
                    // уже распределены роли, опрос второй или третий по счету

                    game.setState(GameState.IN_PROCESS.getState());

                }

                step = getFillGameStepBean(game, sessionId);
                //step.setCell(-1);
                //game.setLastCell(-1);

                //return step;

            }

        } else{
            step = new GameStep();
            step.setGameState(GameState.ENEMY_EXIT.getState());
        }


        return step;
    }


    @POST
    @Path("/game/{gameId}/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameStep makeStep(@PathParam("gameId") int gameId, GameStep step, @CookieParam("JSESSIONID") String sessionId){
        /**
         * gameId
         * cellNumber
         */
        GameDao gameDao = daoFactory.getGameDao();
        Game game = gameDao.getGameById(step.getGameId());

        if(game != null){

            if(game.getState() == GameState.IN_PROCESS.getState()){
                int side = 0;

                if(game.getCreator().getSessionId().equals(sessionId)){
                    side = game.getCreator().getSide();
                } else if(game.getEnemy().getSessionId().equals(sessionId)){
                    side = game.getEnemy().getSide();
                }

                // проверка, чтобы не ходил он же 2 раз
                if(game.getLastStep() != side || game.getCells().length == 0){
                    game.setState(gameDao.makeStep(game.getId(), step.getCell(), side));
                }

                step = getFillGameStepBean(game, sessionId);
                step.setLastStep(game.getLastStep());

            } else{
                step.setGameState(game.getState());

            }

            //return step;
        } else{
            step.setGameState(GameState.ENEMY_EXIT.getState());
        }

        // если игра закончилась
        // нельзя, второй сообщения не получит о результате
//        if(game.getState() == GameState.YOU_WIN.getState() || game.getState() == GameState.YOU_LOOSE.getState()){
//            gameDao.deleteGame(game.getId());
//        }

        return step;
    }

    @GET
    @Path("/game/{gameId}/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameStep getStep(@PathParam("gameId") int gameId, @CookieParam("JSESSIONID") String sessionId){
        /**
         * gameId
         * cellNumber
         */
        GameDao gameDao = daoFactory.getGameDao();
        Game game = gameDao.getGameById(gameId);
        GameStep step = null;

        if(game != null){

            step = getFillGameStepBean(game, sessionId);
            step.setLastStep(game.getLastStep());

            // проверяем время врага и если не вышло, кладем для отправки
            // если вышло, говорим что победа наша
            User currentUser = getCurrentUser(game, sessionId);
            User enemy = getEnemyUser(game, sessionId);
            if(currentUser.getSide() == game.getLastStep() && game.getLastCell() != -1){

                if((new Date()).getTime() - enemy.getTimeStep().getTime() > GameConstants.TIME_ON_STEP_IN_MINUTES.getNumber()){
                    // победа наша
                    step.setGameState(GameState.YOU_WIN.getState());
                } else{
                    // ждем ответа врага
                    step.setEnemyTimeStep((new Date()).getTime() - enemy.getTimeStep().getTime());
                }
            } else{
                // первый ход
                if(enemy.getTimeStep() == null){
                    enemy.setTimeStep(new Date());
                }
            }


            //return step;
        } else{
            step = new GameStep();
            step.setGameState(GameState.ENEMY_EXIT.getState());
        }

        return step;
    }


    private User getCurrentUser(Game game, String sessionId){
        if(game.getCreator().getSessionId().equals(sessionId)){
            return game.getCreator();
        } else if(game.getEnemy().getSessionId().equals(sessionId)){
            return game.getEnemy();
        } else{
            return null;
        }
    }

    private User getEnemyUser(Game game, String sessionId){
        User user = getCurrentUser(game, sessionId);
        if(user == null){
            return null;
        }

        if(user == game.getCreator()){
            return game.getEnemy();
        } else{
            return game.getCreator();
        }
    }


    private int getRandomNumber(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }

    private GameStep getFillGameStepBean(Game game, String sessionId) {
        GameStep step = new GameStep();

        if (sessionId.equals(game.getCreator().getSessionId())) {
            step.setEnemyName(game.getEnemy().getName());
            step.setEnemySide(game.getEnemy().getSide());
            step.setYourName(game.getCreator().getName());
            step.setYourSide(game.getCreator().getSide());
            //step.setWhoStep();

        } else if (sessionId.equals(game.getEnemy().getSessionId())) {
            step.setEnemyName(game.getCreator().getName());
            step.setEnemySide(game.getCreator().getSide());

            step.setYourName(game.getEnemy().getName());
            step.setYourSide(game.getEnemy().getSide());
        }

        if (game.getLastStep() == game.getCreator().getSide()) {
            step.setWhoStep(game.getEnemy().getSide()); // кто сейчас ходит

        } else if (game.getLastStep() == game.getEnemy().getSide()) {
            step.setWhoStep(game.getCreator().getSide()); // кто сейчас ходит
        } else if (game.getLastStep() == GameConstants.NONE.getNumber() || game.getLastStep() == -1) {
            // никто ещё не ходил

            // для случайного выбора кто начинает: крестики или нолики
//            int max = GameConstants.CROSS_PLAYER.getNumber();
//            int min = GameConstants.ZERO_PLAYER.getNumber();
//            int randomNum = getRandomNumber(max, min);
//            step.setWhoStep((randomNum == max) ? max : min);
            step.setWhoStep(GameConstants.ZERO_PLAYER.getNumber());
            game.setLastStep(step.getWhoStep());
            game.setLastCell(-1);
        }

        step.setGameState(game.getState());
        step.setCell(game.getLastCell());
        step.setSide(GameConstants.SQUIRE_SIDE.getNumber());

        step.setGameId(game.getId());

        return step;
    }


}
