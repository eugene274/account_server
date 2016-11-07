package server.model.dao;

import org.hibernate.Session;
import server.database.DbHibernate;
import server.database.TransactionalError;
import server.model.data.Score;
import server.model.data.Token;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by ivan on 06.11.16.
 */
public class TokenHibernate implements TokenDAO {
    private static String ENTITY_NAME = "Tokens";
    private static String ALIAS = "token";
    private Session session = DbHibernate.newSession();

    private org.hibernate.query.Query<Token> getWhereQuery(String ...conditions) {
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS), "");
        for (String condition : conditions) {
            query.add(condition);
        }

        return session.createQuery(query.toString(), Token.class);
    }

    @Override
    public Long insert(Object in) {
        try{
            return (Long) DbHibernate.getTransactional(s -> s.save(in));
        } catch (TransactionalError transactionalError) {
            return -1L;
        }
    }

    @Override
    public Token getById(Long id) {
        return session.get(Token.class, id);
    }

    @Override
    public List<Token> getWhere(String... conditions) {
        return getWhereQuery(conditions).list();
    }

    @Override
    public Token getTokenByTokenString(String tokenString) {
        return session.byNaturalId(Token.class).using("tokenString", tokenString).loadOptional().orElse(null);
    }

    @Override
    public List<Token> getAll() {
        return session.createQuery("from Tokens", Token.class).list();
    }
}
