package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.dto.ShelfSummaryResponseDto;
import com.shivanshu.Assignment.exception.ShelfNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionOccupiedException;
import com.shivanshu.Assignment.model.Shelf;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ShelfRepositoryImpl implements ShelfRepository{
    private final Driver driver;
    public ShelfRepositoryImpl(Driver driver) {
        this.driver = driver;
    }
    @Override
    public Shelf createShelf(String shelfPositionId, Shelf shelf) {

        try (Session session = driver.session()) {

            return session.executeWrite(tx -> {

                // it  checks that my  ShelfPosition exists and not deleted
                Result spResult = tx.run(
                        "MATCH (sp:ShelfPosition {id: $id}) " +
                                "WHERE sp.isDeleted = false " +
                                "RETURN sp",
                        Values.parameters("id", shelfPositionId)
                );

                if (!spResult.hasNext()) {
                    throw new ShelfPositionNotFoundException("ShelfPosition not found or deleted");
                }

                // is checks if my ShelfPosition already has a Shelf
                Result existingShelf = tx.run(
                        "MATCH (sp:ShelfPosition {id: $id})-[:HAS]->(s:Shelf) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s",
                        Values.parameters("id", shelfPositionId)
                );

                if (existingShelf.hasNext()) {
                    throw new ShelfPositionOccupiedException("ShelfPosition already occupied");
                }

                // now we can safely create Shelf and relationship
                tx.run(
                        "MATCH (sp:ShelfPosition {id: $spId}) " +
                                "CREATE (s:Shelf { " +
                                "id: $id, " +
                                "shelfName: $shelfName, " +
                                "partNumber: $partNumber, " +
                                "isDeleted: false }) " +
                                "CREATE (sp)-[:HAS]->(s)",
                        Values.parameters(
                                "spId", shelfPositionId,
                                "id", shelf.getId(),
                                "shelfName", shelf.getShelfName(),
                                "partNumber", shelf.getPartNumber()
                        )
                );

                return shelf;
            });
        }
    }

    @Override
    public Shelf attachExistingShelf(String shelfId, String shelfPositionId) {

        try(Session session = driver.session()){

            return session.executeWrite(tx -> {

                Result result = tx.run(
                        "MATCH (sp:ShelfPosition {id:$spId}) " +
                                "MATCH (s:Shelf {id:$sId}) " +
                                "CREATE (sp)-[:HAS]->(s) " +
                                "RETURN s",
                        Values.parameters(
                                "spId", shelfPositionId,
                                "sId", shelfId
                        )
                );

                Record record = result.single();
                Node node = record.get("s").asNode();

                Shelf shelf = new Shelf();
                shelf.setId(node.get("id").asString());
                shelf.setShelfName(node.get("shelfName").asString());
                shelf.setPartNumber(node.get("partNumber").asString());
                shelf.setDeleted(node.get("isDeleted").asBoolean());

                return shelf;
            });
        }
    }

    @Override
    public Optional<Shelf> findShelfById(String id) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (s:Shelf {id: $id}) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s",
                        Values.parameters("id", id)
                );

                if (!result.hasNext()) {
                    return Optional.empty();
                }

                Record record = result.next();
                Node node = record.get("s").asNode();

                Shelf shelf = new Shelf();
                shelf.setId(node.get("id").asString());
                shelf.setShelfName(node.get("shelfName").asString());
                shelf.setPartNumber(node.get("partNumber").asString());
                shelf.setDeleted(node.get("isDeleted").asBoolean());

                return Optional.of(shelf);
            });
        }
    }

    @Override
    public Optional<Shelf> findShelfByShelfPositionId(String shelfPositionId) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (sp:ShelfPosition {id: $spId})-[:HAS]->(s:Shelf) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s",
                        Values.parameters("spId", shelfPositionId)
                );

                if (!result.hasNext()) {
                    return Optional.empty();
                }

                Record record = result.next();
                Node node = record.get("s").asNode();

                Shelf shelf = new Shelf();
                shelf.setId(node.get("id").asString());
                shelf.setShelfName(node.get("shelfName").asString());
                shelf.setPartNumber(node.get("partNumber").asString());
                shelf.setDeleted(node.get("isDeleted").asBoolean());

                return Optional.of(shelf);
            });
        }
    }

    @Override
    public List<Shelf> findAll() {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (s:Shelf) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s"
                );

                List<Shelf> shelves = new ArrayList<>();

                while (result.hasNext()) {

                    Record record = result.next();
                    Node node = record.get("s").asNode();

                    Shelf shelf = new Shelf();
                    shelf.setId(node.get("id").asString());
                    shelf.setShelfName(node.get("shelfName").asString());
                    shelf.setPartNumber(node.get("partNumber").asString());
                    shelf.setDeleted(node.get("isDeleted").asBoolean());

                    shelves.add(shelf);
                }

                return shelves;
            });
        }
    }

    @Override
    public List<Shelf> getUnattachedShelves() {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (s:Shelf) " +
                                "WHERE s.isDeleted = false " +
                                "AND NOT (s)<-[:HAS]-(:ShelfPosition) " +
                                "RETURN s"
                );

                List<Shelf> shelves = new ArrayList<>();

                while (result.hasNext()) {

                    Record record = result.next();
                    Node node = record.get("s").asNode();

                    Shelf shelf = new Shelf();
                    shelf.setId(node.get("id").asString());
                    shelf.setShelfName(node.get("shelfName").asString());
                    shelf.setPartNumber(node.get("partNumber").asString());
                    shelf.setDeleted(node.get("isDeleted").asBoolean());

                    shelves.add(shelf);
                }

                return shelves;
            });
        }
    }

    @Override
    public Shelf updateShelf(String id, Shelf shelf) {

        try (Session session = driver.session()) {

            return session.executeWrite(tx -> {

                Result check = tx.run(
                        "MATCH (s:Shelf {id: $id}) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s",
                        Values.parameters("id", id)
                );
                //already handled exceptio of shelf not exist in service layer
                //so this code won't even execute as it is already checked and then only
                //update shelf has been called
                if (!check.hasNext()) {
                   return null;
                }

                tx.run(
                        "MATCH (s:Shelf {id: $id}) " +
                                "SET s.shelfName = $shelfName, " +
                                "s.partNumber = $partNumber " +
                                "RETURN s",
                        Values.parameters(
                                "id", id,
                                "shelfName", shelf.getShelfName(),
                                "partNumber", shelf.getPartNumber()
                        )
                );
                //this shelf which was sent by user  was already an updated shelf
                //so we updated our db with it and then we can return it to user
                //as an assurance that shelf is update and we just need to make sure
                //its id is set to the id of previous shelf which was intended to update
                shelf.setId(id);
                return shelf;
            });
        }
    }

    @Override
    public void softDelete(String id) {
        try (Session session = driver.session()) {

            session.executeWrite(tx -> {

                tx.run(
                        "MATCH (sp:ShelfPosition)-[r:HAS]->(s:Shelf {id: $id}) " +
                                "DELETE r",
                        Values.parameters("id", id)
                );

                tx.run(
                        "MATCH (s:Shelf {id: $id}) " +
                                "SET s.isDeleted = true",
                        Values.parameters("id", id)
                );

                return null;
            });
        }
    }

    @Override
    public Optional<ShelfSummaryResponseDto> getShelfSummary(String shelfId) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        """
                        MATCH (s: Shelf {id: $id})
                        WHERE s.isDeleted = false
                        OPTIONAL MATCH (d:Device)-[:HAS]->(sp:ShelfPosition)-[:HAS]->(s)
                        RETURN s.id AS id,
                               s.shelfName AS shelfName,
                               s.partNumber AS partNumber,
                               sp.positionNumber AS positionNumber,
                               d.id AS deviceId,
                               d.deviceName AS deviceName
                        """,
                        Values.parameters("id", shelfId)
                );

                if (!result.hasNext()) {
                    return Optional.empty();
                }

                Record record = result.next();

                String id = record.get("id").asString();
                String shelfName = record.get("shelfName").asString();
                String partNumber = record.get("partNumber").asString();

                Integer positionNumber = record.get("positionNumber").isNull()
                        ? null
                        : record.get("positionNumber").asInt();

                String deviceId = record.get("deviceId").isNull()
                        ? null
                        : record.get("deviceId").asString();

                String deviceName = record.get("deviceName").isNull()
                        ? null
                        : record.get("deviceName").asString();

                return Optional.of(
                        new ShelfSummaryResponseDto(
                                id,
                                shelfName,
                                partNumber,
                                positionNumber,
                                deviceId,
                                deviceName
                        )
                );
            });
        }
    }

}
