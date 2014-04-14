package com.epam.stud.shv.datalayer.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public final class DaoFactoryImpl extends DaoFactory {

    private volatile static DaoFactory instance = null;


    private void DaoFactoryImpl(){
        // this is singleton
    }


    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactoryImpl.class) {
                if (instance == null) {
                    instance = new DaoFactoryImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public GameDao getGameDao() {
        return new GameDaoImpl();
    }
}
