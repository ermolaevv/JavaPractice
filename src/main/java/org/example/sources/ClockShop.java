package org.example.sources;

import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

public class ClockShop
        implements Iterable<IClock> {
    protected Collection<IClock> catalog = new ArrayList<>();
    private final Collection<IOserver> observers = new ArrayList<>();
    private final Map<String, Integer> tableOfNumberOccurrences = new HashMap<>();

    static Connection connection;

    // DB
    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db.s3db");
            System.out.println("Opened database successfully!");
        }
        catch (SQLException e) {
            System.out.println("Can't open DB");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }
    void selectAll() {
        catalog.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from clocks");

            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                int hours = resultSet.getInt("hours");
                int minutes = resultSet.getInt("minutes");
                int seconds = resultSet.getInt("seconds");

                IClock clock;

                if (resultSet.wasNull()) {
                    clock = new Clock(ID, name, price);
                    clock.setStartTime(new Time(hours, minutes));
                }
                else {
                    clock = new Clock_s(ID, name, price);
                    clock.setStartTime(new Time(hours, minutes, seconds));
                }

                catalog.add(clock);
                Integer val = this.tableOfNumberOccurrences.get(clock.getName());
                this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        events();
    }
    public ClockShop() {
        connect();
        selectAll();
    }
    // Observation
    private void events() {
        observers.forEach(
                o -> o.event(this)
        );
    }
    public void sub(IOserver obs) {
        observers.add(obs);
    }

    // Edit
    public void addClock(IClock clock) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO clocks (name, price, hours, minutes, seconds) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, clock.getName());
            statement.setDouble(2, clock.getPrice());

            var timeArray = parseTime(clock);

            statement.setInt(3, timeArray[0]);
            statement.setInt(4, timeArray[1]);

            if (timeArray.length == 3)
                statement.setInt(5, timeArray[2]);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("Creating clock failed, no rows affected.");

            ResultSet generatedKeys = connection.createStatement().executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                clock.setID((int)generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.catalog.add(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);

        events();
    }

    private static int[] parseTime(IClock clock) {
        return Arrays.stream(clock.getTime().split(":")).mapToInt(Integer::parseInt).toArray();
    }

    public void addAllClocks(Collection<IClock> collection) {
        this.catalog.addAll(collection);
        collection.forEach(clock -> {
            Integer val = this.tableOfNumberOccurrences.get(clock.getName());
            this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
        });
        events();
    }

    public void removeClock(IClock clock) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM clocks WHERE ID = ?");
            statement.setInt(1, clock.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.catalog.remove(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val - 1);
        events();
    }

    public void clear() {
        this.catalog.clear();
        this.tableOfNumberOccurrences.clear();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clocks");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        events();
    }
    // Modify
    public void setAllTime(Time time) {
        this.catalog.forEach(clock -> {
            clock.setStartTime(time);
            syncTime(clock);
        });
        events();
    }

    public void syncTime(IClock clock) {
        var timeArray = parseTime(clock);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clocks SET hours = ?, minutes = ?, seconds = ? WHERE ID = " + clock.getID());

            preparedStatement.setInt(1, timeArray[0]);
            preparedStatement.setInt(2, timeArray[1]);
            if (timeArray.length == 3)
                preparedStatement.setInt(3, timeArray[2]);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveAllTime(Time time) {
        this.catalog.forEach(clock -> {
            clock.timeShift(time);
            syncTime(clock);
        });
        events();
    }
    // Process
    public IClock getInfoMaxPrice() {
        if (!catalog.isEmpty())
            return Collections.max(this.catalog, (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
        return null;
    }

    public String getMostCommon() {
        if (this.catalog.isEmpty())
            return "н/д";
        Map.Entry<String, Integer> max =
                Collections.max(this.tableOfNumberOccurrences.entrySet(), Map.Entry.comparingByValue());
        return max.getKey();
    }

    public Collection<String> getListOfNames() {
        return new TreeSet<>(this.tableOfNumberOccurrences.keySet());
    }

    public String toString() {
        return this.catalog.toString();
    }

    @Override
    public Iterator<IClock> iterator() {
        return this.catalog.iterator();
    }

    @Override
    public void forEach(Consumer<? super IClock> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<IClock> spliterator() {
        return Iterable.super.spliterator();
    }
}