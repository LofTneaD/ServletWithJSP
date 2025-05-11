package dbService.dao;

import dbService.data.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }
    //    TODO: getUser
    public UserProfile get(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }


    public void insertUser(String login, String password, String email) throws HibernateException {
        session.save(new UserProfile(login, password, email));
    }
}