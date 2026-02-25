package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.model.ShelfPosition;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ShelfPositionRepositoryImpl implements ShelfPositionRepository{
    private final Driver driver;

    public ShelfPositionRepositoryImpl(Driver driver) {
        this.driver = driver;
    }
    @Override
    public Optional<ShelfPosition> findById(String id) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (sp:ShelfPosition) " +
                                "WHERE sp.id = $id AND sp.isDeleted = false " +
                                "RETURN sp",
                        Values.parameters("id", id)
                );

                if (!result.hasNext()) {
                    return Optional.empty();
                }

                Record record = result.next();
                Node node = record.get("sp").asNode();

                ShelfPosition sp = new ShelfPosition(
                        node.get("id").asString(),
                        node.get("positionNumber").asInt(),
                        node.get("deviceId").asString(),
                        node.get("isDeleted").asBoolean()
                );

                return Optional.of(sp);
            });
        }
    }
    @Override
    public List<ShelfPosition> findByDeviceId(String deviceId) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (d:Device {id: $deviceId})-[:HAS]->(sp:ShelfPosition) " +
                                "WHERE sp.isDeleted = false " +
                                "RETURN sp",
                        Values.parameters("deviceId", deviceId)
                );

                List<ShelfPosition> list = new ArrayList<>();

                while (result.hasNext()) {

                    Record record = result.next();
                    Node node = record.get("sp").asNode();

                    list.add(new ShelfPosition(
                            node.get("id").asString(),
                            node.get("positionNumber").asInt(),
                            node.get("deviceId").asString(),
                            node.get("isDeleted").asBoolean()
                    ));
                }

                return list;
            });
        }
    }
    @Override
    public List<ShelfPosition> findAll() {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (sp:ShelfPosition) " +
                                "WHERE sp.isDeleted = false " +
                                "RETURN sp"
                );

                List<ShelfPosition> list = new ArrayList<>();

                while (result.hasNext()) {

                    Record record = result.next();
                    Node node = record.get("sp").asNode();

                    list.add(new ShelfPosition(
                            node.get("id").asString(),
                            node.get("positionNumber").asInt(),
                            node.get("deviceId").asString(),
                            node.get("isDeleted").asBoolean()
                    ));
                }

                return list;
            });
        }
    }
    @Override
    public void softDelete(String id) {
        try (Session session = driver.session()) {

            session.executeWrite(tx -> {

                // Remove relationship if shelf exists
                tx.run(
                        "MATCH (sp:ShelfPosition {id: $id}) " +
                                "OPTIONAL MATCH (sp)-[r:HAS]->(s:Shelf) " +
                                "DELETE r",
                        Values.parameters("id", id)
                );

                // Soft delete shelf position
                tx.run(
                        "MATCH (sp:ShelfPosition {id: $id}) " +
                                "SET sp.isDeleted = true",
                        Values.parameters("id", id)
                );

                return null;
            });
        }
    }
}
