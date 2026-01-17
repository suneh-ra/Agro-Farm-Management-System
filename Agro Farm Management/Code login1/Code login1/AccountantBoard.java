/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;

public class AccountantBoard extends Application{
    private Stage primaryStage;
    private Label statusLabel;
    private TextArea messageTextArea;
    
    private List<String> financialNotes = new ArrayList<>();
    private TextArea noteTextArea;
    private ListView<String> notesListView;
    
    private double totalExpenses = 0;
    private final String salesDataFile = "sales_data.txt";
    private final String expenseDataFile = "expense_data.txt";

    public static void main(String[] args) {
        launch(args);
    }
    
    public AccountantBoard(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeBoard();
        initializeMessageTextArea(); 
        checkAndUpdateDashboard();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeBoard();
         initializeMessageTextArea();
        checkAndUpdateDashboard();

    }

    private void initializeBoard() {
        primaryStage.setTitle("Accountant Dashboard");

        VBox accountantDashboard = new VBox();
        accountantDashboard.setAlignment(Pos.CENTER); 
        accountantDashboard.setSpacing(20);
        accountantDashboard.setPadding(new Insets(20));
        
        Label welcomeLabel = new Label("Welcome to Accountant Dashboard");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: royalblue;"); 
        accountantDashboard.getChildren().add(welcomeLabel);

        Button salesButton = new Button("Sales Report");
        salesButton.setOnAction(e -> openSalesReport());

        Button expenseButton = new Button("Expense Tracking");
        expenseButton.setOnAction(e -> openExpenseTracking());

        Button financialButton = new Button("Financial Decision");
        financialButton.setOnAction(e -> openFinancialDecisionSupport());

        accountantDashboard.getChildren().addAll(salesButton, expenseButton, financialButton);

        Label titleLabel = new Label("Messages:");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;"); 
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

     
        accountantDashboard.getChildren().addAll(titleLabel, statusLabel);
       
        Scene accountantScene = new Scene(accountantDashboard, 400, 400);
        primaryStage.setScene(accountantScene);
        primaryStage.show();
    }
    
    public void showMessage(String message) {
        statusLabel.setText(message);
    }

    private void initializeMessageTextArea() {
        messageTextArea = new TextArea();
        messageTextArea.setEditable(false);
        messageTextArea.setPrefRowCount(5); 
    }

    public void addMessage(String message) {
        messageTextArea.appendText(message + "\n");
    }
  

    private void checkAndUpdateDashboard() {
    try (BufferedReader reader = new BufferedReader(new FileReader("financial_note_status.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String note = parts[0];
            String status = parts[1];
            updateDashboardWithStatus(note, status);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 private void updateDashboardWithStatus(String note, String status) {
    String message = "Financial note: " + note + " has been " + status.toLowerCase();
    showMessage(message);
}
 

 private void openSalesReport() {

        primaryStage.setTitle("Sales Report");

        VBox salesLayout = new VBox(20);
        salesLayout.setPadding(new Insets(20));

        TableView<SalesEntry> salesTable = new TableView<>();
        salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<SalesEntry, LocalDate> dateColumn = new TableColumn<>("Date");
        TableColumn<SalesEntry, String> productColumn = new TableColumn<>("Product");
        TableColumn<SalesEntry, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<SalesEntry, Double> priceColumn = new TableColumn<>("Price");
        TableColumn<SalesEntry, Double> amountColumn = new TableColumn<>("Amount");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        salesTable.getColumns().addAll(dateColumn, productColumn, quantityColumn, priceColumn, amountColumn);

        loadSalesDataFromFile(salesTable);
        double totalMilkSalesAmount = loadMilkSalesDataFromWorkerBoard();

        Text totalMilkSalesText = new Text("Total Milk Sales Amount: $" + String.format("%.2f", totalMilkSalesAmount));

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

        TextField productTextField = new TextField();
        productTextField.setPromptText("Product");

        TextField quantityTextField = new TextField();
        quantityTextField.setPromptText("Quantity");

        TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");

        Text totalAmountText = new Text("Total Amount:");

          Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            LocalDate date = datePicker.getValue();
            String product = productTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            double amount = quantity * price;

           
    double previousTotalAmount = salesTable.getItems().stream().mapToDouble(SalesEntry::getAmount).sum();
    
            SalesEntry newEntry = new SalesEntry(date, product, quantity, price);
            salesTable.getItems().add(newEntry);
            saveSalesEntryToFile(newEntry);

    double newTotalAmount = previousTotalAmount + amount + totalMilkSalesAmount;

    totalAmountText.setText("Total Amount: " + String.format("%.2f", newTotalAmount));
    
            datePicker.setValue(null);
            productTextField.clear();
            quantityTextField.clear();
            priceTextField.clear();
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> initializeBoard());

        salesLayout.getChildren().addAll(datePicker, productTextField, quantityTextField, priceTextField, submitButton, salesTable,totalMilkSalesText, totalAmountText, backButton);

        Scene salesScene = new Scene(salesLayout, 500, 550);
        primaryStage.setScene(salesScene);
        primaryStage.show();
    }

    private void saveSalesEntryToFile(SalesEntry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(salesDataFile, true))) {
            String line = entry.getDate() + "," + entry.getProduct() + "," + entry.getQuantity() + "," + entry.getPrice() + "," + entry.getAmount();
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save sales entry to file.");
        }
    }

    private double loadMilkSalesDataFromWorkerBoard() {
        double totalMilkSalesAmount = 0.0;
        String milkSalesDataFile = "milk_sales_data.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(milkSalesDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    double amount = Double.parseDouble(parts[3]); 
                    totalMilkSalesAmount += amount;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load milk sales data from milk_sales_data.txt file.");
        }

        return totalMilkSalesAmount;
    }
    
    
    private void loadSalesDataFromFile(TableView<SalesEntry> salesTable) {
        salesTable.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(salesDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { 
                    try {
                        LocalDate date = LocalDate.parse(parts[0].trim());
                        String product = parts[1].trim();
                        int quantity = Integer.parseInt(parts[2].trim());
                        double price = Double.parseDouble(parts[3].trim());
                        double amount = quantity * price; 

                        SalesEntry entry = new SalesEntry(date, product, quantity, price);
                        Platform.runLater(() -> salesTable.getItems().add(entry));
                    } catch (DateTimeParseException | NumberFormatException e) {
                        System.err.println("Error parsing data: " + line);
                    }
                } else {
                    System.err.println("Invalid data format in sales report: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load sales data from file.");
        }
    }

    public static class SalesEntry {
        private final ObjectProperty<LocalDate> date;
        private final StringProperty product;
        private final IntegerProperty quantity;
        private final DoubleProperty price;
        private final DoubleProperty amount;

        public SalesEntry(LocalDate date, String product, int quantity, double price) {
    this.date = new SimpleObjectProperty<>(date);
    this.product = new SimpleStringProperty(product);
    this.quantity = new SimpleIntegerProperty(quantity);
    this.price = new SimpleDoubleProperty(price);
    this.amount = new SimpleDoubleProperty(quantity * price);
}


        public LocalDate getDate() {
            return date.get();
        }

        public void setDate(LocalDate date) {
            this.date.set(date);
        }

        public String getProduct() {
            return product.get();
        }

        public void setProduct(String product) {
            this.product.set(product);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public double getPrice() {
            return price.get();
        }

        public void setPrice(double price) {
            this.price.set(price);
        }

        public double getAmount() {
            return amount.get();
        }

        public void setAmount(double amount) {
            this.amount.set(amount);
        }

        public ObjectProperty<LocalDate> dateProperty() {
            return date;
        }

        public StringProperty productProperty() {
            return product;
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public DoubleProperty priceProperty() {
            return price;
        }

        public DoubleProperty amountProperty() {
            return amount;
        }
    }


    private void openExpenseTracking() {
    primaryStage.setTitle("Expense Tracker");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setVgap(10);
    grid.setHgap(10);

    Label dateLabel = new Label("Date:");
    DatePicker datePicker = new DatePicker();

    Label descriptionLabel = new Label("Description:");
    TextField descriptionTextField = new TextField();

    Label amountLabel = new Label("Amount:");
    TextField amountTextField = new TextField();

    Button submitButton = new Button("Submit");
    TextArea reportTextArea = new TextArea();
    reportTextArea.setEditable(false);
    
    TableView<ExpenseEntry> tableView = new TableView<>();
    tableView.setEditable(true);
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ExpenseEntry, String> dateColumn = new TableColumn<>("Date");
    TableColumn<ExpenseEntry, String> descriptionColumn = new TableColumn<>("Description");
    TableColumn<ExpenseEntry, Double> amountColumn = new TableColumn<>("Amount");

    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    tableView.getColumns().addAll(dateColumn, descriptionColumn, amountColumn);

    grid.add(dateLabel, 0, 0);
    grid.add(datePicker, 1, 0);
    grid.add(descriptionLabel, 0, 1);
    grid.add(descriptionTextField, 1, 1);
    grid.add(amountLabel, 0, 2);
    grid.add(amountTextField, 1, 2);
    grid.add(submitButton, 0, 3);
    grid.add(reportTextArea, 1, 3, 2, 1);
    grid.add(tableView, 0, 4, 3, 1);

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> initializeBoard()); 
    grid.add(backButton, 0, 5); 

    submitButton.setOnAction(e -> {
        String date = datePicker.getValue().toString();
        String description = descriptionTextField.getText();
        String amount = amountTextField.getText();
        generateReport(date, description, amount, tableView, reportTextArea);
    });
  
    loadExpenseDataFromFile(tableView);

    Scene scene = new Scene(grid, 500, 550);
    primaryStage.setScene(scene);
    primaryStage.show();
}
    
    private void loadExpenseDataFromFile(TableView<ExpenseEntry> tableView) {
    try {
        File expenseFile = new File(expenseDataFile);
        Scanner scanner = new Scanner(expenseFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 3) {
                String date = parts[0].trim();
                String description = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());

                ExpenseEntry entry = new ExpenseEntry(date, description, amount);
                
                tableView.getItems().add(entry);
            } else {
                System.err.println("Invalid line format: " + line);
            }
        }

        scanner.close();
    } catch (FileNotFoundException e) {
        System.err.println("Expense file not found: " + expenseDataFile);
    } catch (NumberFormatException e) {
        System.err.println("Invalid amount format in expense file.");
    }
}

    
private void generateReport(String date, String description, String amount, TableView<ExpenseEntry> tableView, TextArea reportTextArea) {
    try {
        if (date.isEmpty() || description.isEmpty() || amount.isEmpty()) {
            reportTextArea.setText("Please fill in all fields.");
            return;
        }

        double amountValue = Double.parseDouble(amount);
        tableView.getItems().add(new ExpenseEntry(date, description, amountValue));
        saveExpenseEntryToFile(date, description, amountValue);

        totalExpenses += amountValue;
        String report = "Total Expenses: $" + totalExpenses;
        reportTextArea.setText(report);
    } catch (NumberFormatException e) {
        reportTextArea.setText("Invalid amount. Please enter a numeric value.");
    }
}

private void saveExpenseEntryToFile(String date, String description, double amount) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(expenseDataFile, true))) {
        writer.write(date + "," + description + "," + amount + "\n");
    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to save expense entry to file.");
    }
}

  private void openFinancialDecisionSupport() {
    primaryStage.setTitle("Financial Decision");

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));

    CategoryAxis dateAxis = new CategoryAxis();
    NumberAxis totalAmountAxis = new NumberAxis();
    BarChart<String, Number> dateChart = new BarChart<>(dateAxis, totalAmountAxis);
    dateChart.setTitle("Total Sale Amount vs Date");
    dateAxis.setLabel("Date");
    totalAmountAxis.setLabel("Total Amount");

    XYChart.Series<String, Number> dateSeries = new XYChart.Series<>();
    dateSeries.setName("Total Amount");

 
    try (BufferedReader reader = new BufferedReader(new FileReader(salesDataFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String date = parts[0];
                double amount = Double.parseDouble(parts[4]);
                dateSeries.getData().add(new XYChart.Data<>(date, amount));
            } else {
               
                System.err.println("Invalid data format in sales report: " + line);
            }
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    dateChart.getData().add(dateSeries);

   
    CategoryAxis descriptionAxis = new CategoryAxis();
    NumberAxis totalAmountAxis2 = new NumberAxis();
    BarChart<String, Number> descriptionChart = new BarChart<>(descriptionAxis, totalAmountAxis2);
    descriptionChart.setTitle("Expenses vs Description");
    descriptionAxis.setLabel("Description");
    totalAmountAxis2.setLabel("Expenses");

    XYChart.Series<String, Number> descriptionSeries = new XYChart.Series<>();
    descriptionSeries.setName("Expenses");

    
    try (BufferedReader reader = new BufferedReader(new FileReader(expenseDataFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String description = parts[1];
                double amount = Double.parseDouble(parts[2]);
                descriptionSeries.getData().add(new XYChart.Data<>(description, amount));
            } else {
              
                System.err.println("Invalid data format in expense report: " + line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to read expense report file.");
    } catch (NumberFormatException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to parse amount from expense report file.");
    }

    dateChart.getData().add(dateSeries);
    descriptionChart.getData().add(descriptionSeries);

       layout.setSpacing(20);
        layout.setPadding(new Insets(20));

        noteTextArea = new TextArea();
        noteTextArea.setPromptText("Enter financial note...");

     Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> submitFinancialNote());

        Button backButton = new Button("Back");
    backButton.setOnAction(e -> initializeBoard()); 
        
    layout.getChildren().addAll(dateChart, descriptionChart, noteTextArea, submitButton, backButton); 
    Scene scene = new Scene(layout, 800, 600);
    primaryStage.setScene(scene);

    primaryStage.show();
}


private void saveFinancialNotes(String notes) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("financial_notes.txt", true))) {
        writer.write(notes + "\n");
        sendNotesToManager();
    } catch (IOException e) {
    }
}

 private void sendNotesToManager() {
        System.out.println("Sending notes to Manager Dashboard:");
        for (String note : financialNotes) {
            System.out.println("- " + note);
        }
    }
 
 private void submitFinancialNote() {
    String note = noteTextArea.getText();
    if (!note.isEmpty()) {
        financialNotes.add(note);
        saveFinancialNotes(note); 
        sendNotesToManager(); 
        showAlert("Submitted", "Financial note submitted successfully!");
        noteTextArea.clear();
    } else {
        showAlert("Error", "Please enter a financial note.");
    }
}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static class ExpenseEntry {
        private final SimpleStringProperty date;
        private final SimpleStringProperty description;
        private final SimpleDoubleProperty amount;

        public ExpenseEntry(String date, String description, double amount) {
            this.date = new SimpleStringProperty(date);
            this.description = new SimpleStringProperty(description);
            this.amount = new SimpleDoubleProperty(amount);
        }

        public String getDate() {
            return date.get();
        }

        public String getDescription() {
            return description.get();
        }

        public double getAmount() {
            return amount.get();
        }
    }
    
   
     public void showDashboard() {
        primaryStage.show();
    }
}
