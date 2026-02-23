package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.model.Device;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class DeviceRepositoryImpl implements DeviceRepository {
    private final Driver driver;
    public DeviceRepositoryImpl(Driver driver) {
        this.driver = driver;
    }
    @Override
    public Device createDevice(Device device) {

        try (Session session = driver.session()) {

            return session.executeWrite(tx -> {

                // Creating my Device node
                tx.run(
                        "CREATE (d:Device { " +
                                "id: $id, " +
                                "deviceName: $deviceName, " +
                                "partNumber: $partNumber, " +
                                "buildingName: $buildingName, " +
                                "deviceType: $deviceType, " +
                                "numberOfShelfPositions: $numberOfShelfPositions, " +
                                "isDeleted: false })",
                        Values.parameters(
                                "id", device.getId(),
                                "deviceName", device.getDeviceName(),
                                "partNumber", device.getPartNumber(),
                                "buildingName", device.getBuildingName(),
                                "deviceType", device.getDeviceType(),
                                "numberOfShelfPositions", device.getNumberOfShelfPositions()
                        )
                );

                //  Create ShelfPositions for each device
                for (int i = 1; i <= device.getNumberOfShelfPositions(); i++) {

                    String shelfPositionId = UUID.randomUUID().toString();

                    tx.run(
                            "MATCH (d:Device {id: $deviceId}) " +
                                    "CREATE (sp:ShelfPosition { " +
                                    "id: $spId, " +
                                    "positionNumber: $positionNumber, " +
                                    "deviceId: $deviceId, " +
                                    "isDeleted: false }) " +
                                    "CREATE (d)-[:HAS]->(sp)",
                            Values.parameters(
                                    "deviceId", device.getId(),
                                    "spId", shelfPositionId,
                                    "positionNumber", i
                            )
                    );
                }

                return device;
            });
        }
    }
    @Override
    public Optional<Device> findById(String id) {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (d:Device {id: $id}) " +
                                "WHERE d.isDeleted = false " +
                                "RETURN d",
                        Values.parameters("id", id)
                );

                if (!result.hasNext()) {
                    return Optional.empty();
                }

                Record record = result.next();
                Node node = record.get("d").asNode();

                Device device = new Device(
                        node.get("id").asString(),
                        node.get("deviceName").asString(),
                        node.get("partNumber").asString(),
                        node.get("buildingName").asString(),
                        node.get("deviceType").asString(),
                        node.get("numberOfShelfPositions").asInt(),
                        node.get("isDeleted").asBoolean()
                );

                return Optional.of(device);
            });
        }
    }
    @Override
    public List<Device> findAll() {

        try (Session session = driver.session()) {

            return session.executeRead(tx -> {

                Result result = tx.run(
                        "MATCH (d:Device) " +
                                "WHERE d.isDeleted = false " +
                                "RETURN d"
                );

                List<Device> devices = new ArrayList<>();

                while (result.hasNext()) {

                    Record record = result.next();
                    Node node = record.get("d").asNode();

                    devices.add(new Device(
                            node.get("id").asString(),
                            node.get("deviceName").asString(),
                            node.get("partNumber").asString(),
                            node.get("buildingName").asString(),
                            node.get("deviceType").asString(),
                            node.get("numberOfShelfPositions").asInt(),
                            node.get("isDeleted").asBoolean()
                    ));
                }

                return devices;
            });
        }
    }
    @Override
    public void softDelete(String id) {

        try (Session session = driver.session()) {

            session.executeWrite(tx -> {

                tx.run(
                        "MATCH (d:Device {id: $id}) " +
                                "SET d.isDeleted = true",
                        Values.parameters("id", id)
                );

                tx.run(
                        "MATCH (d:Device {id: $id})-[:HAS]->(sp:ShelfPosition) " +
                                "SET sp.isDeleted = true",
                        Values.parameters("id", id)
                );

                return null;
            });
        }
    }
}
