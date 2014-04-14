package com.epam.stud.shv.datalayer.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DaoFactory {

    public abstract UserDao getUserDao();

    public abstract GameDao getGameDao();

    public static DaoFactory getFactory(){
        //add switch and parameter in future
        return DaoFactoryImpl.getInstance();
    }


}
