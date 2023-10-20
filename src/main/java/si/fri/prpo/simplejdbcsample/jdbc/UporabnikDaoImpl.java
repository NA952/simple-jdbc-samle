package si.fri.prpo.simplejdbcsample.jdbc;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao {

    private static UporabnikDaoImpl instance;
    private static final Logger log = Logger.getLogger(UporabnikDaoImpl.class.getName());

    private Connection connection;

    public static UporabnikDaoImpl getInstance() {

        if (instance == null) {
            instance = new UporabnikDaoImpl();
        }

        return instance;
    }

    public UporabnikDaoImpl() {
        connection = getConnection();
    }

    @Override
    public Connection getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();
        } catch (Exception e) {
            log.severe("Cannot get connection: " + e.toString());
        }
        return null;
    }

    @Override
    public Entiteta vrni(int id) {

        PreparedStatement ps = null;

        try {

            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {
        //programska koda za vstavljanje uporabnikov
        PreparedStatement preparedStatement = null;

        try {
            if (ent instanceof Uporabnik) {
                String sql = "INSERT INTO uporabnik(ime, priimek, uporabniskoime) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, ((Uporabnik) ent).getIme());
                preparedStatement.setString(2, ((Uporabnik) ent).getPriimek());
                preparedStatement.setString(3, ((Uporabnik) ent).getUporabniskoIme());

                preparedStatement.execute();
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void odstrani(int id) {
        Statement st = null;

        try {
            st = connection.createStatement();
            String sql = "DELETE FROM uporabnik WHERE id = " + id;
            st.execute(sql);
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent) {
        //programska koda za posodabljanje uporabnikov
        PreparedStatement preparedStatement = null;

        try {
            if (ent instanceof Uporabnik) {
                String sql = "UPDATE uporabnik SET ime = ?, priimek = ?, uporabniskoime = ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ((Uporabnik) ent).getIme());
                preparedStatement.setString(2, ((Uporabnik) ent).getPriimek());
                preparedStatement.setString(3, ((Uporabnik) ent).getUporabniskoIme());
                preparedStatement.setInt(4, ent.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public List<Entiteta> vrniVse() {

        List<Entiteta> entitete = new ArrayList<Entiteta>();
        Statement st = null;

        try {

            st = connection.createStatement();
            String sql = "SELECT * FROM uporabnik";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                entitete.add(getUporabnikFromRS(rs));
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return entitete;
    }

    private static Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("id");
        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");
        return new Uporabnik(id, ime, priimek, uporabniskoIme);

    }
}
