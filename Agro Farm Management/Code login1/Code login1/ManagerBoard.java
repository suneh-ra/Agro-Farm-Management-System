package login1;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ManagerBoard extends Application {
     private Stage primaryStage;
    private int totalCattle = 0; 
    private Label totalCattleLabel; 
    private List<Cattle> cattleList = new ArrayList<>(); 
    private TableView<Cattle> tableView = new TableView<>();
    
    public static void main(String[] args) {
        launch(args);
    }
   
public ManagerBoard(Stage primaryStage, AccountantBoard accountantBoard) {
        this.primaryStage = primaryStage;
        initializeBoard();
    }
    public ManagerBoard(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initializeBoard();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeBoard();
    }

    private void initializeBoard() {
         primaryStage.setTitle("Farm Manager Dashboard");

       
        VBox dashboard = new VBox(20);
        dashboard.setAlignment(Pos.CENTER); 
        dashboard.setPadding(new Insets(20));
        
         Label welcomeLabel = new Label("Welcome to Farm Manager Dashboard");
         welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: green;"); 
        dashboard.getChildren().add(welcomeLabel);

        Button livestockButton = new Button("Livestock Details");
        Button salesButton = new Button("View Sales Data");
        Button expenseButton = new Button("View Expense Data");
        Button financialButton = new Button("Financial Graph");
        Button analyticsButton = new Button("Financial Analysis ");
        Button annualReportButton = new Button("Report");

        livestockButton.setOnAction(e -> openLivestockDetails());
        salesButton.setOnAction(e -> openViewSalesData());
        expenseButton.setOnAction(e -> openViewExpenseData());
        financialButton.setOnAction(e -> openFinancialDecisionSupport());
        analyticsButton.setOnAction(e -> openAnalytics());
        
        annualReportButton.setOnAction(e -> openAnnualReport());
 
     dashboard.getChildren().addAll(livestockButton, salesButton, expenseButton, financialButton, analyticsButton, annualReportButton);

        Scene scene = new Scene(dashboard, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   private void openAnnualReport() {
    Stage annualReportStage = new Stage();
    openAnnualReport annualReport = new openAnnualReport();
    annualReport.start(annualReportStage);
}

    
    private void openLivestockDetails() {
        Stage livestockStage = new Stage();
        livestockStage.setTitle("Livestock Details");

        totalCattleLabel = new Label("Total Cattle: " + totalCattle); 

        TextField earTagField = new TextField();
        earTagField.setPromptText("Animal ID");
        TextField breedField = new TextField();
        breedField.setPromptText("Breed");
        TextField sexField = new TextField();
        sexField.setPromptText("Sex");
        TextField colorField = new TextField();
        colorField.setPromptText("Color");

        Button addCattleButton = new Button("Add Cattle");
        addCattleButton.setOnAction(e -> {
            
            String animalID = earTagField.getText();
            String breed = breedField.getText();
            String sex = sexField.getText();
            String color = colorField.getText();

          
            if (!animalID.isEmpty() && !breed.isEmpty() && !sex.isEmpty() && !color.isEmpty()) {
                cattleList.add(new Cattle(animalID, breed, sex, color));

                totalCattle++;
                totalCattleLabel.setText("Total Cattle: " + totalCattle);

                earTagField.clear();
                breedField.clear();
                sexField.clear();
                colorField.clear();

                saveCattleInformation();

                tableView.getItems().clear();
                tableView.getItems().addAll(cattleList);
            } else {
               
              showAlert("Error", "Please fill in all fields.");
            }
        });

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        TableColumn<Cattle, String> earTagColumn = new TableColumn<>("Animal ID");
        earTagColumn.setCellValueFactory(new PropertyValueFactory<>("animalID"));
        TableColumn<Cattle, String> breedColumn = new TableColumn<>("Breed");
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        TableColumn<Cattle, String> sexColumn = new TableColumn<>("Sex");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        TableColumn<Cattle, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableView.getColumns().addAll(earTagColumn, breedColumn, sexColumn, colorColumn);

    loadCattleInformation(); 
    
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.addRow(0, new Label("Animal ID:"), earTagField);
        inputGrid.addRow(1, new Label("Breed:"), breedField);
        inputGrid.addRow(2, new Label("Sex:"), sexField);
        inputGrid.addRow(3, new Label("Color:"), colorField);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(totalCattleLabel, inputGrid, addCattleButton, tableView);

        Scene scene = new Scene(layout, 600, 500);
        livestockStage.setScene(scene);
        livestockStage.show();

        loadCattleInformation();
    }

    private void saveCattleInformation() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cattle_information.txt"))) {
            for (Cattle cattle : cattleList) {
                writer.write("Animal ID: " + cattle.getAnimalID() + "\n");
                writer.write("Breed: " + cattle.getBreed() + "\n");
                writer.write("Sex: " + cattle.getSex() + "\n");
                writer.write("Color: " + cattle.getColor() + "\n\n");
            }
            System.out.println("Cattle information saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void loadCattleInformation() {
    try (BufferedReader reader = new BufferedReader(new FileReader("cattle_information.txt"))) {
        String animalID = "";
        String breed = "";
        String sex = "";
        String color = "";

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Animal ID:")) {
                animalID = line.substring(11).trim();
            } else if (line.startsWith("Breed:")) {
                breed = line.substring(7).trim();
            } else if (line.startsWith("Sex:")) {
                sex = line.substring(5).trim();
            } else if (line.startsWith("Color:")) {
                color = line.substring(7).trim();
                
                cattleList.add(new Cattle(animalID, breed, sex, color));
              
                animalID = "";
                breed = "";
                sex = "";
                color = "";
            }
        }

        totalCattle = cattleList.size();
        totalCattleLabel.setText("Total Cattle: " + totalCattle);
        System.out.println("Cattle information loaded from file.");

       
        tableView.getItems().addAll(cattleList);
    } catch (IOException e) {
        System.err.println("Error loading cattle information from file: " + e.getMessage());
    }
}
  
    public static class Cattle {
        private String animalID;
        private String breed;
        private String sex;
        private String color;

        public Cattle(String animalID, String breed, String sex, String color) {
            this.animalID = animalID;
            this.breed = breed;
            this.sex = sex;
            this.color = color;
        }

        public String getAnimalID() {
            return animalID;
        }

        public String getBreed() {
            return breed;
        }

        public String getSex() {
            return sex;
        }

        public String getColor() {
            return color;
        }
    }

   
    private void openViewSalesData() {
        Stage salesDataStage = new Stage();
    salesDataStage.setTitle("View Sales Data");

    TextArea salesTextArea = new TextArea();
    salesTextArea.setEditable(false);
    salesTextArea.setPrefRowCount(10); 

    StringBuilder salesData = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader("sales_data.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            salesData.append(line).append("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    salesTextArea.setText(salesData.toString());

    VBox vbox = new VBox();
    vbox.getChildren().addAll(salesTextArea);

    Scene scene = new Scene(vbox);
    salesDataStage.setScene(scene);
    salesDataStage.show();
    }

    private void openViewExpenseData() {
        Stage expenseDataStage = new Stage();
    expenseDataStage.setTitle("View Expense Data");

    TextArea expenseTextArea = new TextArea();
    expenseTextArea.setEditable(false);
    expenseTextArea.setPrefRowCount(10); 

    StringBuilder expenseData = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader("expense_data.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            expenseData.append(line).append("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    expenseTextArea.setText(expenseData.toString());

    VBox vbox = new VBox();
    vbox.getChildren().addAll(expenseTextArea);

    Scene scene = new Scene(vbox);
    expenseDataStage.setScene(scene);
    expenseDataStage.show();
      
    }

   private final String salesDataFile = "sales_data.txt";
    private final String expenseDataFile = "expense_data.txt";
    
 private void openFinancialDecisionSupport() {
    primaryStage.setTitle("Financial Graphs");

   
    CategoryAxis dateAxis = new CategoryAxis();
    NumberAxis totalAmountAxis = new NumberAxis();
    BarChart<String, Number> dateChart = new BarChart<>(dateAxis, totalAmountAxis);
    dateChart.setTitle("Total Sales Amount vs Date");
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
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    descriptionChart.getData().add(descriptionSeries);

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(dateChart, descriptionChart);
    
    Button returnButton = new Button("Back");
    returnButton.setOnAction(e -> {
        ManagerBoard managerBoard = new ManagerBoard(primaryStage);
        managerBoard.start(primaryStage);
    });

    layout.getChildren().add(returnButton);
    Scene scene = new Scene(layout, 600, 500);
    primaryStage.setScene(scene);
    
    primaryStage.show();
}

   private void openAnalytics() {
    Stage analyticsStage = new Stage();
    analyticsStage.setTitle("Finencial Analysis");

    GridPane layout = new GridPane();
    layout.setPadding(new Insets(20));
    layout.setHgap(10);
    layout.setVgap(10);

    ListView<String> financialNotesListView = new ListView<>();
    refreshFinancialNotes(financialNotesListView);

    layout.add(new Label("Financial Notes:"), 0, 0);
    layout.add(financialNotesListView, 0, 1);

    Button approveButton = new Button("Approve");
    approveButton.setOnAction(e -> approveFinancialNoteAndShowMessage(financialNotesListView.getSelectionModel().getSelectedItem()));
    
    Button rejectButton = new Button("Reject");
    rejectButton.setOnAction(e -> rejectFinancialNoteAndShowMessage(financialNotesListView.getSelectionModel().getSelectedItem()));

    Button addMoneyButton = new Button("Invest");
    addMoneyButton.setOnAction(e -> addMoney());

    layout.add(approveButton, 1, 1);
    layout.add(rejectButton, 2, 1);
    layout.add(addMoneyButton, 3, 1);

    Scene scene = new Scene(layout, 600, 500);
    analyticsStage.setScene(scene);

    analyticsStage.show();
}

private void refreshFinancialNotes(ListView<String> financialNotesListView) {
   
    financialNotesListView.getItems().clear();
    try (BufferedReader reader = new BufferedReader(new FileReader("financial_notes.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            financialNotesListView.getItems().add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private AccountantBoard accountantDashboard;
     private void approveFinancialNoteAndShowMessage(String note) {
         
    if (note != null) {
        
        System.out.println("Financial note approved:\n" + note);
        showSuccessMessage("Success", "Financial note approved.");
     
    } else {
        showAlert("Error", "Please select a financial note to approve.");
    }
    updateFinancialNoteStatus(note, "APPROVED");
   
    notifyAccountantDashboard();
}

private void rejectFinancialNoteAndShowMessage(String note) {
    if (note != null) {
        
        System.out.println("Financial note rejected:\n" + note);
        showSuccessMessage("Success", "Financial note rejected.");
    } else {
        showAlert("Error", "Please select a financial note to reject.");
    }
     updateFinancialNoteStatus(note, "REJECTED");
    notifyAccountantDashboard();
     
}

private void showSuccessMessage(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
}


private void updateFinancialNoteStatus(String note, String status) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("financial_note_status.txt", true))) {
        writer.write(note + "," + status + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void notifyAccountantDashboard() {
}


private void addMoney() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Investment");
    dialog.setHeaderText("Enter the amount of money to add:");
    dialog.setContentText("Amount:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(amount -> {
        try {
            double amountToAdd = Double.parseDouble(amount);

           
           try (BufferedWriter writer = new BufferedWriter(new FileWriter("added_money.txt", true))) {
                 writer.write(amountToAdd + "\n"); 
                System.out.println("Money added successfully.");
                showSuccessMessage("Success", "Money added successfully.");
              
                if (accountantDashboard != null) {
                    accountantDashboard.showMessage("Money added by manager: " + amountToAdd);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save information about adding money.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount entered. Please enter a valid number.");
        }
    });
    System.out.println("Money added successfully.");
}


public class openAnnualReport {

    private TableView<AnnualEntry> tableView;
    private double totalSales = 0;
    private double totalExpenses = 0;
    private double totalAddedMoney = 0;

    public static void main(String[] args) {
        launch(args);
    }

   // @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Report");

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<AnnualEntry, String> dateColumn = new TableColumn<>("Date");
        TableColumn<AnnualEntry, Double> salesColumn = new TableColumn<>("Total Sales");
        TableColumn<AnnualEntry, Double> expensesColumn = new TableColumn<>("Total Expenses");
        TableColumn<AnnualEntry, Double> addedMoneyColumn = new TableColumn<>("Investment");
        TableColumn<AnnualEntry, Double> profitColumn = new TableColumn<>("Profit");

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        salesColumn.setCellValueFactory(cellData -> cellData.getValue().salesProperty().asObject());
        expensesColumn.setCellValueFactory(cellData -> cellData.getValue().expensesProperty().asObject());
        addedMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().addedMoneyProperty().asObject());
        profitColumn.setCellValueFactory(cellData -> cellData.getValue().profitProperty().asObject());

        tableView.getColumns().addAll(dateColumn, salesColumn, expensesColumn, addedMoneyColumn, profitColumn);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(new Label("Report"), tableView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadSalesData();
        loadExpenseData();
        loadAddedMoneyData();
        updateTableView();
    }

    private void loadSalesData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("sales_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    double amount = Double.parseDouble(parts[4]);
                    totalSales += amount;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenseData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("expense_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    double amount = Double.parseDouble(parts[2]);
                    totalExpenses += amount;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadAddedMoneyData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("added_money.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                 double amount = Double.parseDouble(line.trim());
                totalAddedMoney += amount;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateTableView() {
        double profit = totalSales - totalExpenses;
        LocalDate today = LocalDate.now();
        AnnualEntry entry = new AnnualEntry(today, totalSales, totalExpenses, totalAddedMoney, profit);
        tableView.getItems().add(entry);
    }

    public static class AnnualEntry {
        private final LocalDate date;
        private final double sales;
        private final double expenses;
        private final double addedMoney;
        private final double profit;

        public AnnualEntry(LocalDate date, double sales, double expenses, double addedMoney, double profit) {
            this.date = date;
            this.sales = sales;
            this.expenses = expenses;
            this.addedMoney = addedMoney;
            this.profit = profit;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getSales() {
            return sales;
        }

        public double getExpenses() {
            return expenses;
        }

        public double getAddedMoney() {
            return addedMoney;
        }

        public double getProfit() {
            return profit;
        }

        public StringProperty dateProperty() {
            return new SimpleStringProperty(date.toString());
        }

        public DoubleProperty salesProperty() {
            return new SimpleDoubleProperty(sales);
        }

        public DoubleProperty expensesProperty() {
            return new SimpleDoubleProperty(expenses);
        }

        public DoubleProperty addedMoneyProperty() {
            return new SimpleDoubleProperty(addedMoney);
        }

        public DoubleProperty profitProperty() {
            return new SimpleDoubleProperty(profit);
        }
    }
}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
     public void showDashboard() {
        primaryStage.show();
    }
}
