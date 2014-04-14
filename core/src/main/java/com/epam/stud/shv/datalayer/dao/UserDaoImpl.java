package com.epam.stud.shv.datalayer.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoImpl implements UserDao {

    //TODO если юзать сессион айди, нафиг не надо хешировать
    @Override
    public String getHashPassword() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
