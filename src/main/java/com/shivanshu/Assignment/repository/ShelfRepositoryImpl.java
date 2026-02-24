package com.shivanshu.Assignment.repository;

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
                    throw new RuntimeException("ShelfPosition not found or deleted");
                }

                // is checks if my ShelfPosition already has a Shelf
                Result existingShelf = tx.run(
                        "MATCH (sp:ShelfPosition {id: $id})-[:HAS]->(s:Shelf) " +
                                "WHERE s.isDeleted = false " +
                                "RETURN s",
                        Values.parameters("id", shelfPositionId)
                );

                if (existingShelf.hasNext()) {
                    throw new RuntimeException("ShelfPosition already occupied");
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
    public void softDelete(String id) {

        try (Session session = driver.session()) {

            session.executeWrite(tx -> {

                //  Soft deleting shelf
                tx.run(
                        "MATCH (s:Shelf {id: $id}) " +
                                "SET s.isDeleted = true",
                        Values.parameters("id", id)
                );

                //  Remove relationship to free ShelfPosition
                tx.run(
                        "MATCH (sp:ShelfPosition)-[r:HAS]->(s:Shelf {id: $id}) " +
                                "DELETE r",
                        Values.parameters("id", id)
                );

                return null;
            });
        }
    }

}
