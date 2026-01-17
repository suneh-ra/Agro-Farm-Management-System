package login1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class WorkerBoard {

    private final Stage primaryStage;

    public WorkerBoard(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeDashboard();
    }

    private void initializeDashboard() {
        VBox workerdashboard = new VBox();
        workerdashboard.setAlignment(Pos.CENTER); 
        workerdashboard.setSpacing(20);
        workerdashboard.setPadding(new Insets(20));
        
        Label welcomeLabel = new Label("Welcome to Farm Worker Dashboard");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:blue;");
        workerdashboard.getChildren().add(welcomeLabel);

        Button recordDataButton = new Button("Record Health Data");
          recordDataButton.setOnAction(e -> {
    RecordHealth recordHealth = new RecordHealth();
    recordHealth.start(new Stage());
    });

       Button scheduleButton = new Button("Veterinary Appointment Scheduler");
         scheduleButton.setOnAction(e -> {
    Vetvisitscheduler1 vetvisitscheduler1 = new Vetvisitscheduler1();
    vetvisitscheduler1.start(new Stage());
    });

        Button breedManageButton = new Button("Assist in Breeding Management");
      breedManageButton.setOnAction(e -> {
            BreedingManagement breedingManagement = new BreedingManagement();
            breedingManagement.start(new Stage());
        });

        Button feedingButton = new Button("Feeding Schedule Management");
        feedingButton.setOnAction(e -> {
            Feeding feeding = new Feeding();
            feeding.start(new Stage());
        });

        Button milkCollectButton = new Button("Milk Collection");
        milkCollectButton.setOnAction(e -> {
            MilkCollection milkCollection = new MilkCollection(primaryStage, this);
            milkCollection.start(new Stage());
        });

        Button milkSaleButton = new Button("Milk Sales");
        milkSaleButton.setOnAction(e -> {
            MilkSale milkSale = new MilkSale(primaryStage, this);
            milkSale.start(new Stage());
        });

        workerdashboard.getChildren().addAll(recordDataButton, scheduleButton, breedManageButton, feedingButton, milkCollectButton, milkSaleButton);

        Scene dashboardScene = new Scene(workerdashboard, 400, 400);
        primaryStage.setScene(dashboardScene);
    }

    public void showDashboard() {
        primaryStage.show();
    }

public class RecordHealth extends Application {

    private TableView<HealthRecord> tableView = new TableView<>();
    private ObservableList<HealthRecord> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Health Record");
        
        
        loadDataFromFile();

        
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label tokenLabel = new Label("Token:");
        TextField tokenField = new TextField();
        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();
        Label temperatureLabel = new Label("Temperature:");
        TextField temperatureField = new TextField();
        Label weightLabel = new Label("Weight:");
        TextField weightField = new TextField();
        Label treatmentHistoryLabel = new Label("Treatment History:");
        TextArea treatmentHistoryArea = new TextArea();
        Label healthStatusLabel = new Label("Health Status:");
        TextField healthStatusField = new TextField();
        Button recordButton = new Button("Record");

        
        TableColumn<HealthRecord, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<HealthRecord, String> tokenCol = new TableColumn<>("Token");
        tokenCol.setCellValueFactory(new PropertyValueFactory<>("token"));
        TableColumn<HealthRecord, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<HealthRecord, String> temperatureCol = new TableColumn<>("Temperature");
        temperatureCol.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        TableColumn<HealthRecord, String> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        TableColumn<HealthRecord, String> treatmentHistoryCol = new TableColumn<>("Treatment History");
        treatmentHistoryCol.setCellValueFactory(new PropertyValueFactory<>("treatmentHistory"));
        TableColumn<HealthRecord, String> healthStatusCol = new TableColumn<>("Health Status");
        healthStatusCol.setCellValueFactory(new PropertyValueFactory<>("healthStatus"));
        tableView.getColumns().addAll(nameCol, tokenCol, ageCol, temperatureCol, weightCol, treatmentHistoryCol, healthStatusCol);
        tableView.setItems(data);

        
        recordButton.setOnAction(event -> {
            String name = nameField.getText();
            String token = tokenField.getText();
            int age = Integer.parseInt(ageField.getText());
            String temperature = temperatureField.getText();
            String weight = weightField.getText();
            String treatmentHistory = treatmentHistoryArea.getText();
            String healthStatus = healthStatusField.getText();
            if (!name.isEmpty() && !token.isEmpty() && !temperature.isEmpty() && !weight.isEmpty()) {
                data.add(new HealthRecord(name, token, age, temperature, weight, treatmentHistory, healthStatus));
                nameField.clear();
                tokenField.clear();
                ageField.clear();
                temperatureField.clear();
                weightField.clear();
                treatmentHistoryArea.clear();
                healthStatusField.clear();
                saveDataToFile(); 
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields.");
                alert.showAndWait();
            }
        });

       
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(tokenLabel, 0, 1);
        grid.add(tokenField, 1, 1);
        grid.add(ageLabel, 0, 2);
        grid.add(ageField, 1, 2);
        grid.add(temperatureLabel, 0, 3);
        grid.add(temperatureField, 1, 3);
        grid.add(weightLabel, 0, 4);
        grid.add(weightField, 1, 4);
        grid.add(treatmentHistoryLabel, 0, 5);
        grid.add(treatmentHistoryArea, 1, 5);
        grid.add(healthStatusLabel, 0, 6);
        grid.add(healthStatusField, 1, 6);
        grid.add(recordButton, 1, 7);
        grid.add(tableView, 0, 8, 2, 1);

        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("health_records.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) { 
                    String name = parts[0];
                    String token = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String temperature = parts[3];
                    String weight = parts[4];
                    String treatmentHistory = parts[5];
                    String healthStatus = parts[6];
                    data.add(new HealthRecord(name, token, age, temperature, weight, treatmentHistory, healthStatus));
                } else {
                    System.err.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while loading data from the file.");
            alert.showAndWait();
        }
    }

    
    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("health_records.txt"))) {
            for (HealthRecord record : data) {
                writer.write(record.getName() + "," +
                        record.getToken() + "," +
                        record.getAge() + "," +
                        record.getTemperature() + "," +
                        record.getWeight() + "," +
                        record.getTreatmentHistory() + "," +
                        record.getHealthStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while saving data to the file.");
            alert.showAndWait();
        }
    }

    
    public static class HealthRecord {
        private final String name;
        private final String token;
        private final int age;
        private final String temperature;
        private final String weight;
        private final String treatmentHistory;
        private final String healthStatus;

        public HealthRecord(String name, String token, int age, String temperature, String weight, String treatmentHistory, String healthStatus) {
            this.name = name;
            this.token = token;
            this.age = age;
            this.temperature = temperature;
            this.weight = weight;
            this.treatmentHistory = treatmentHistory;
            this.healthStatus = healthStatus;
        }

        public String getName() {
            return name;
        }

        public String getToken() {
            return token;
        }

        public int getAge() {
            return age;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getWeight() {
            return weight;
        }

        public String getTreatmentHistory() {
            return treatmentHistory;
        }

        public String getHealthStatus() {
            return healthStatus;
        }
    }
}


public class Vetvisitscheduler1 extends Application {

    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Veterinary Appointment Scheduler");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label animalIdLabel = new Label("Animal ID:");
        TextField animalIdTextField = new TextField();

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeTextField = new TextField();

        Button addButton = new Button("Schedule Appointment");
        Button deleteButton = new Button("Cancel Appointment");

        TableView<Appointment> appointmentTable = new TableView<>();
        appointmentTable.setItems(appointments);
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Appointment, String> animalIdColumn = new TableColumn<>("Animal ID");
        animalIdColumn.setCellValueFactory(cellData -> cellData.getValue().animalIdProperty());

        TableColumn<Appointment, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn<Appointment, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());

        appointmentTable.getColumns().addAll(animalIdColumn, dateColumn, timeColumn);

        addButton.setOnAction(event -> {
            String animalId = animalIdTextField.getText();
            String date = datePicker.getValue().toString();
            String time = timeTextField.getText();
            Appointment appointment = new Appointment(animalId, date, time);
            appointments.add(appointment);
            saveAppointmentToFile(appointment);
        });

        deleteButton.setOnAction(event -> {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                appointments.remove(selectedAppointment);
            }
        });

        grid.add(animalIdLabel, 0, 0);
        grid.add(animalIdTextField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeTextField, 1, 2);
        grid.add(addButton, 0, 3);
        grid.add(deleteButton, 1, 3);
        grid.add(appointmentTable, 0, 4, 2, 1);

// Load data from file
        loadDataFromFile();
        
        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveAppointmentToFile(Appointment appointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointments.txt", true))) {
            writer.write(appointment.getAnimalId() + "," + appointment.getDate() + "," + appointment.getTime() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String animalId = parts[0];
                    String date = parts[1];
                    String time = parts[2];
                    Appointment appointment = new Appointment(animalId, date, time);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public class Appointment {
        private final String animalId;
        private final String date;
        private final String time;

        public Appointment(String animalId, String date, String time) {
            this.animalId = animalId;
            this.date = date;
            this.time = time;
        }

        public String getAnimalId() {
            return animalId;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public StringProperty animalIdProperty() {
            return new SimpleStringProperty(animalId);
        }

        public StringProperty dateProperty() {
            return new SimpleStringProperty(date);
        }

        public StringProperty timeProperty() {
            return new SimpleStringProperty(time);
        }
    }
}

   public class BreedingManagement extends Application {

    ObservableList<BreedingRecord> breedingRecords = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Breeding Management System");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label parent1Label = new Label("Parent 1 id:");
        TextField parent1TextField = new TextField();

        Label parent2Label = new Label("Parent 2 id:");
        TextField parent2TextField = new TextField();

        Label breedingDateLabel = new Label("Breeding Date:");
        DatePicker breedingDatePicker = new DatePicker();

        Label dueDateLabel = new Label("Due Date:");
        DatePicker dueDatePicker = new DatePicker();

        Label notesLabel = new Label("Notes:");
        TextArea notesTextArea = new TextArea();

        Button addButton = new Button("Add Breeding Record");

        TableView<BreedingRecord> breedingTable = new TableView<>();
        breedingTable.setItems(breedingRecords);
        breedingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BreedingRecord, String> parent1Column = new TableColumn<>("Parent 1 id");
        parent1Column.setCellValueFactory(cellData -> cellData.getValue().parent1Property());

        TableColumn<BreedingRecord, String> parent2Column = new TableColumn<>("Parent 2 id");
        parent2Column.setCellValueFactory(cellData -> cellData.getValue().parent2Property());

        TableColumn<BreedingRecord, LocalDate> breedingDateColumn = new TableColumn<>("Breeding Date");
        breedingDateColumn.setCellValueFactory(cellData -> cellData.getValue().breedingDateProperty());

        TableColumn<BreedingRecord, LocalDate> dueDateColumn = new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());

        TableColumn<BreedingRecord, String> notesColumn = new TableColumn<>("Notes");
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

        breedingTable.getColumns().addAll(parent1Column, parent2Column, breedingDateColumn, dueDateColumn, notesColumn);

        addButton.setOnAction(event -> {
            String parent1 = parent1TextField.getText();
            String parent2 = parent2TextField.getText();
            LocalDate breedingDate = breedingDatePicker.getValue();
            LocalDate dueDate = dueDatePicker.getValue();
            String notes = notesTextArea.getText();
            BreedingRecord newRecord = new BreedingRecord(parent1, parent2, breedingDate, dueDate, notes);
            breedingRecords.add(newRecord);
            saveBreedingRecordToFile(newRecord);
        });

        grid.add(parent1Label, 0, 0);
        grid.add(parent1TextField, 1, 0);
        grid.add(parent2Label, 0, 1);
        grid.add(parent2TextField, 1, 1);
        grid.add(breedingDateLabel, 0, 2);
        grid.add(breedingDatePicker, 1, 2);
        grid.add(dueDateLabel, 0, 3);
        grid.add(dueDatePicker, 1, 3);
        grid.add(notesLabel, 0, 4);
        grid.add(notesTextArea, 1, 4);
        grid.add(addButton, 0, 5);
        grid.add(breedingTable, 0, 6, 2, 1);

        // Load data from file
        loadDataFromFile();
        
        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

     private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("breeding_records.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String parent1 = parts[0];
                    String parent2 = parts[1];
                    LocalDate breedingDate = LocalDate.parse(parts[2]);
                    LocalDate dueDate = LocalDate.parse(parts[3]);
                    String notes = parts[4];
                    BreedingRecord record = new BreedingRecord(parent1, parent2, breedingDate, dueDate, notes);
                    breedingRecords.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    public class BreedingRecord {
        private final String parent1;
        private final String parent2;
        private final LocalDate breedingDate;
        private final LocalDate dueDate;
        private final String notes;

        public BreedingRecord(String parent1, String parent2, LocalDate breedingDate, LocalDate dueDate, String notes) {
            this.parent1 = parent1;
            this.parent2 = parent2;
            this.breedingDate = breedingDate;
            this.dueDate = dueDate;
            this.notes = notes;
        }

        public String getParent1() {
            return parent1;
        }

        public String getParent2() {
            return parent2;
        }

        public LocalDate getBreedingDate() {
            return breedingDate;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public String getNotes() {
            return notes;
        }

        public StringProperty parent1Property() {
            return new SimpleStringProperty(parent1);
        }

        public StringProperty parent2Property() {
            return new SimpleStringProperty(parent2);
        }

        public ObjectProperty<LocalDate> breedingDateProperty() {
            return new SimpleObjectProperty<>(breedingDate);
        }

        public ObjectProperty<LocalDate> dueDateProperty() {
            return new SimpleObjectProperty<>(dueDate);
        }

        public StringProperty notesProperty() {
            return new SimpleStringProperty(notes);
        }
    }

    private void saveBreedingRecordToFile(BreedingRecord breedingRecord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("breeding_records.txt", true))) {
            writer.write(breedingRecord.getParent1() + "," + breedingRecord.getParent2() + "," +
                    breedingRecord.getBreedingDate() + "," + breedingRecord.getDueDate() + "," +
                    breedingRecord.getNotes() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    public static class MilkCollection extends Application {
        private final String milkCollectFile = "milk_collect_data.txt"; 
        private Stage primaryStage;
        private final WorkerBoard workerBoard;

        public MilkCollection(Stage primaryStage, WorkerBoard workerBoard) {
            this.primaryStage = primaryStage;
            this.workerBoard = workerBoard;
        }

        private TableView<MilkSalesEntry> salesTable;
        private TextField totalQuantityTextField;

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Milk Collection Data");

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));

            HBox dateInput = new HBox(10);
            Label dateLabel = new Label("Date:");
            DatePicker datePicker = new DatePicker();
            dateInput.getChildren().addAll(dateLabel, datePicker);

            HBox quantityInput = new HBox(10);
            Label quantityLabel = new Label("Milk Quantity (Liters):");
            TextField quantityTextField = new TextField();
            quantityInput.getChildren().addAll(quantityLabel, quantityTextField);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                LocalDate date = datePicker.getValue();
                double quantity = Double.parseDouble(quantityTextField.getText());

                MilkSalesEntry newEntry = new MilkSalesEntry(date, quantity);
                salesTable.getItems().add(newEntry);
                saveMilkSalesEntryToFile(newEntry);

                datePicker.setValue(null);
                quantityTextField.clear();

                updateTotalQuantity();
            });

            salesTable = new TableView<>();
            salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<MilkSalesEntry, LocalDate> dateColumn = new TableColumn<>("Date");
            TableColumn<MilkSalesEntry, Double> quantityColumn = new TableColumn<>("Milk Quantity (Liters)");

            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            salesTable.getColumns().addAll(dateColumn, quantityColumn);

            totalQuantityTextField = new TextField();
            totalQuantityTextField.setEditable(false);
            totalQuantityTextField.setPromptText("Total Milk Quantity");

            Label totalLabel = new Label("Total Milk Quantity:");
            HBox totalBox = new HBox(10);
            totalBox.getChildren().addAll(totalLabel, totalQuantityTextField);

            layout.getChildren().addAll(dateInput, quantityInput, submitButton, salesTable, totalBox);

            loadDataFromFile();
            updateTotalQuantity();
    
            Scene scene = new Scene(layout, 600, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void saveMilkSalesEntryToFile(MilkSalesEntry entry) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(milkCollectFile, true))) {
                writer.write(entry.getDate() + "," + entry.getQuantity() + "\n");
                writer.flush(); 
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save milk sales entry to file.");
            }
        }

        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }

        private void updateTotalQuantity() {
            ObservableList<MilkSalesEntry> entries = salesTable.getItems();
            double total = entries.stream().mapToDouble(MilkSalesEntry::getQuantity).sum();
            totalQuantityTextField.setText(String.valueOf(total));
        }
        
         private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(milkCollectFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    double quantity = Double.parseDouble(parts[1]);
                    MilkSalesEntry entry = new MilkSalesEntry(date, quantity);
                    salesTable.getItems().add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load data from file.");
        }
    }
        public static class MilkSalesEntry {
            private final SimpleObjectProperty<LocalDate> date;
            private final SimpleDoubleProperty quantity;

            public MilkSalesEntry(LocalDate date, double quantity) {
                this.date = new SimpleObjectProperty<>(date);
                this.quantity = new SimpleDoubleProperty(quantity);
            }

            public LocalDate getDate() {
                return date.get();
            }

            public double getQuantity() {
                return quantity.get();
            }
        }
    }
    
    public static class MilkSale extends Application {
        private final String salesDataFile = "milk_sales_data.txt";
        private Stage primaryStage;
        private WorkerBoard workerBoard;

        public MilkSale(Stage primaryStage, WorkerBoard workerBoard) {
            this.primaryStage = primaryStage;
            this.workerBoard = workerBoard;
        }

        private TableView<MilkSaleEntry> salesTable;
        private TextField totalAmountTextField;

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Milk Sales Data");

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));

            HBox dateInput = new HBox(10);
            Label dateLabel = new Label("Date:");
            DatePicker datePicker = new DatePicker();
            dateInput.getChildren().addAll(dateLabel, datePicker);

            HBox priceInput = new HBox(10);
            Label priceLabel = new Label("Price per liter:");
            TextField priceTextField = new TextField();
            priceInput.getChildren().addAll(priceLabel, priceTextField);

            HBox quantityInput = new HBox(10);
            Label quantityLabel = new Label("Milk Quantity (Liters):");
            TextField quantityTextField = new TextField();
            quantityInput.getChildren().addAll(quantityLabel, quantityTextField);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                LocalDate date = datePicker.getValue();
                double price = Double.parseDouble(priceTextField.getText());
                double quantity = Double.parseDouble(quantityTextField.getText());
                double amount = price * quantity;

                MilkSaleEntry newEntry = new MilkSaleEntry(date, price, quantity, amount);
                salesTable.getItems().add(newEntry);
                saveSalesEntryToFile(newEntry);

                datePicker.setValue(null);
                priceTextField.clear();
                quantityTextField.clear();

                updateTotalAmount();
            });

            salesTable = new TableView<>();
            salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

            TableColumn<MilkSaleEntry, LocalDate> dateColumn = new TableColumn<>("Date");
            TableColumn<MilkSaleEntry, Double> priceColumn = new TableColumn<>("Price per liter");
            TableColumn<MilkSaleEntry, Double> quantityColumn = new TableColumn<>("Milk Quantity (Liters)");
            TableColumn<MilkSaleEntry, Double> amountColumn = new TableColumn<>("Amount");

            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

            salesTable.getColumns().addAll(dateColumn, priceColumn, quantityColumn, amountColumn);

            totalAmountTextField = new TextField();
            totalAmountTextField.setEditable(false);
            totalAmountTextField.setPromptText("Total Sales Amount");

            Label totalLabel = new Label("Total Sales Amount:");
            HBox totalBox = new HBox(10);
            totalBox.getChildren().addAll(totalLabel, totalAmountTextField);

           
            layout.getChildren().addAll(dateInput, priceInput, quantityInput, submitButton, salesTable, totalBox);

            loadDataFromFile();
           updateTotalAmount();

            Scene scene = new Scene(layout, 600, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void saveSalesEntryToFile(MilkSaleEntry entry) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(salesDataFile, true))) {
                writer.write(entry.getDate() + "," + entry.getPrice() + "," + entry.getQuantity() + "," + entry.getAmount() + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save sales entry to file.");
            }
        }

 private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(salesDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    double price = Double.parseDouble(parts[1]);
                    double quantity = Double.parseDouble(parts[2]);
                    double amount = Double.parseDouble(parts[3]);
                    MilkSaleEntry entry = new MilkSaleEntry(date, price, quantity, amount);
                    salesTable.getItems().add(entry);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load data from file.");
        }
    }

    private void updateTotalAmount() {
        ObservableList<MilkSaleEntry> entries = salesTable.getItems();
        double total = entries.stream().mapToDouble(MilkSaleEntry::getAmount).sum();
        totalAmountTextField.setText(String.valueOf(total));
    }
        
         private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }
         
        public static class MilkSaleEntry {
            private final SimpleObjectProperty<LocalDate> date;
            private final SimpleDoubleProperty price;
            private final SimpleDoubleProperty quantity;
            private final SimpleDoubleProperty amount;

            public MilkSaleEntry(LocalDate date, double price, double quantity, double amount) {
                this.date = new SimpleObjectProperty<>(date);
                this.price = new SimpleDoubleProperty(price);
                this.quantity = new SimpleDoubleProperty(quantity);
                this.amount = new SimpleDoubleProperty(amount);
            }

            public LocalDate getDate() {
                return date.get();
            }

            public double getPrice() {
                return price.get();
            }

            public double getQuantity() {
                return quantity.get();
            }

            public double getAmount() {
                return amount.get();
            }
        }
    }
    
     public static class Feeding extends Application {
        private final String feedingDataFile = "feeding_schedule_data.txt";

        public static void main(String[] args) {
            launch(args);
        }

        private TableView<FeedingScheduleEntry> scheduleTable;
        private TextField scheduleField;
        private TextField timeField;
        private DatePicker datePicker;

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Feeding Schedule Management");

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));

            HBox scheduleInput = new HBox(10);
            Label scheduleLabel = new Label("Feeding Schedule:");
            scheduleField = new TextField();
            scheduleField.setPromptText("Enter feeding essentials");
            scheduleInput.getChildren().addAll(scheduleLabel, scheduleField);

            HBox timeInput = new HBox(10);
            Label timeLabel = new Label("Feeding Time:");
            timeField = new TextField();
            timeField.setPromptText("e.g., 08:00 AM");
            timeInput.getChildren().addAll(timeLabel, timeField);

            HBox dateInput = new HBox(10);
            Label dateLabel = new Label("Feeding Date:");
            datePicker = new DatePicker();
            dateInput.getChildren().addAll(dateLabel, datePicker);

            Button addButton = new Button("Add Schedule");
            addButton.setOnAction(e -> addSchedule());

            Button deleteButton = new Button("Delete Schedule");
            deleteButton.setOnAction(e -> deleteSchedule());

            scheduleTable = new TableView<>();
            scheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<FeedingScheduleEntry, String> scheduleColumn = new TableColumn<>("Feeding Essentials");
            scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));

            TableColumn<FeedingScheduleEntry, String> timeColumn = new TableColumn<>("Feeding Time");
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

            TableColumn<FeedingScheduleEntry, LocalDate> dateColumn = new TableColumn<>("Date");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            scheduleTable.getColumns().addAll(scheduleColumn, timeColumn, dateColumn);

            layout.getChildren().addAll(scheduleInput, timeInput, dateInput, new HBox(10, addButton, deleteButton), scheduleTable);

            // Load data from file
            loadDataFromFile();
        
            Scene scene = new Scene(layout, 600, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void addSchedule() {
            String newSchedule = scheduleField.getText().trim();
            String newTime = timeField.getText().trim();
            LocalDate newDate = datePicker.getValue();
            if (!newSchedule.isEmpty() && !newTime.isEmpty() && newDate != null) {
                FeedingScheduleEntry newEntry = new FeedingScheduleEntry(newSchedule, newTime, newDate);
                scheduleTable.getItems().add(newEntry);
                saveScheduleEntryToFile(newEntry);

                
                scheduleField.clear();
                timeField.clear();
                datePicker.setValue(null);
            } else {
                showAlert("Error", "Please enter both feeding schedule, time, and date.");
            }
        }

        private void deleteSchedule() {
            FeedingScheduleEntry selectedEntry = scheduleTable.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                scheduleTable.getItems().remove(selectedEntry);
                deleteEntryFromFile(selectedEntry);
            } else {
                showAlert("Error", "Please select a feeding schedule to delete.");
            }
        }

        private void saveScheduleEntryToFile(FeedingScheduleEntry entry) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedingDataFile, true))) {
                writer.write(entry.getSchedule() + "," + entry.getTime() + "," + entry.getDate() + "\n");
            } catch (IOException e) {
                showAlert("Error", "Failed to save feeding schedule entry to file.");
            }
        }

        private void deleteEntryFromFile(FeedingScheduleEntry entry) {
        }

        private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(feedingDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String schedule = parts[0];
                    String time = parts[1];
                    LocalDate date = LocalDate.parse(parts[2]);
                    FeedingScheduleEntry entry = new FeedingScheduleEntry(schedule, time, date);
                    scheduleTable.getItems().add(entry);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load data from file.");
        }
    }
        
        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }

        public static class FeedingScheduleEntry {
            private final SimpleStringProperty schedule;
            private final SimpleStringProperty time;
            private final SimpleStringProperty date;

            public FeedingScheduleEntry(String schedule, String time, LocalDate date) {
                this.schedule = new SimpleStringProperty(schedule);
                this.time = new SimpleStringProperty(time);
                this.date = new SimpleStringProperty(date.toString());
            }

            public String getSchedule() {
                return schedule.get();
            }

            public String getTime() {
                return time.get();
            }

            public String getDate() {
                return date.get();
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        WorkerBoard workerBoard = new WorkerBoard(primaryStage);
        workerBoard.showDashboard();
    }
}
