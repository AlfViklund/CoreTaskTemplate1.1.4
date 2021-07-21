package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name1", "LastName1", (byte)50);
        userService.saveUser("Name2", "LastName2", (byte)50);
        userService.saveUser("Name3", "LastName3", (byte)50);
        userService.saveUser("Name4", "LastName4", (byte)50);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();


        /*UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();
        userDao.saveUser("Name1", "LastName1", (byte)50);
        userDao.saveUser("Name2", "LastName2", (byte)50);
        userDao.saveUser("Name3", "LastName3", (byte)50);
        userDao.saveUser("Name4", "LastName4", (byte)50);

        List<User> list = userDao.getAllUsers();
        Iterator<User> iterator = list.listIterator();
        while (iterator.hasNext()) {
            System.out.println(((User) iterator.next()).toString());
        }

        userDao.cleanUsersTable();
        userDao.dropUsersTable();*/
    }
}
