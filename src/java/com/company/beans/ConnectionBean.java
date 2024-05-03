package com.company.beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
//import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.servlet.http.Part;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@Named(value = "connectionBean")
@SessionScoped
public class ConnectionBean implements Serializable {

    private String username;
    private Integer port;
    private String password;
    private String selectedDatabaseName;
    private String selectedTabName;
    private List<String> databaseNames;
    private List<String> filteredDatabaseNames;
    private List<String> filteredTablesNames;
    private List<String> databaseTables;
    private List<String> columnNames; // Define columnNames as a class-level variable
    private List<List<String>> tableDataList;
    private Part file;
    private List<Row> rowsWithErrors;
    private boolean error = false;
    private String ErrorMessage;
    private String searchKeyword;
    private boolean importState;
    private List<String> errMessages = new ArrayList<>();
    private boolean fileChosen = false;
    private boolean importButtonDisabled = true;

    //----------------------------------------------------------------------------------------
  
    public ConnectionBean() {
    }

    
    //----------------------------------------------------------------------------------------
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for port
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getDatabaseNames() {
        return databaseNames;
    }

    public void setDatabaseNames(List<String> databaseNames) {
        this.databaseNames = databaseNames;
    }

    public List<String> getDatabaseTables() {
        return databaseTables;
    }

    public void setDatabaseTables(List<String> databaseTables) {
        this.databaseTables = databaseTables;
    }

    public String getSelectedDatabaseName() {
        return selectedDatabaseName;
    }

    public void setSelectedDatabaseName(String selectedDatabaseName) {
        this.selectedDatabaseName = selectedDatabaseName;
    }

    public String getSelectedTabName() {
        return selectedTabName;
    }

    public void setSelectedTabName(String selectedTabName) {
        this.selectedTabName = selectedTabName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<List<String>> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<List<String>> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<String> getFilteredDatabaseNames() {
        return filteredDatabaseNames;
    }

    public void setFilteredDatabaseNames(List<String> filteredDatabaseNames) {
        this.filteredDatabaseNames = filteredDatabaseNames;
    }

    public List<String> getFilteredTablesNames() {
        return filteredTablesNames;
    }

    public void setFilteredTablesNames(List<String> filteredTablesNames) {
        this.filteredTablesNames = filteredTablesNames;
    }

    public List<Row> getRowsWithErrors() {
        return rowsWithErrors;
    }

    public void setRowsWithErrors(List<Row> rowsWithErrors) {
        this.rowsWithErrors = rowsWithErrors;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
   
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public boolean isImportState() {
        return importState;
    }

    public void setImportState(boolean importState) {
        this.importState = importState;
    }

    public List<String> getErrMessages() {
        return errMessages;
    }

    public void setErrMessages(List<String> errMessages) {
        this.errMessages = errMessages;
    }

    public boolean isFileChosen() {
        return fileChosen;
    }

    public void setFileChosen(boolean fileChosen) {
        this.fileChosen = fileChosen;
    }

    public boolean isImportButtonDisabled() {
        return importButtonDisabled;
    }

    public void setImportButtonDisabled(boolean importButtonDisabled) {
        this.importButtonDisabled = importButtonDisabled;
    }

    
    
    
    
    
    
    //----------------------------------------------------------------------------------------

  
    String url = "jdbc:postgresql://127.0.0.1:";
    
    //----------------------------------------------------------------------------------------
    
    public String createCon() throws SQLException, IOException{
         
        boolean connectionCreated = false;
        String outcome = null;
        ErrorMessage = null;

        try {
            Class.forName("org.postgresql.Driver");
            Connection cnx = DriverManager.getConnection(url+port+"/?",username, password);
            PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM pg_database");
            ResultSet rs = pstmt.executeQuery();
            
            
            databaseNames = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString(2));
                databaseNames.add(rs.getString(2));
            }
            connectionCreated = true;
            filteredDatabaseNames = new ArrayList<>();
            setFilteredDatabaseNames(databaseNames);

            
            System.out.println("connect");
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erreur");
            }
            catch (SQLException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getMessage().contains("username")) {
                    ErrorMessage = "Error: Invalid username.";
                } else if (ex.getMessage().contains("password")) {
                    ErrorMessage = "Error: Invalid password.";
                } else if (ex.getMessage().contains("port")) {
                    ErrorMessage = "Error: Invalid port.";
                } else {
                    ErrorMessage = "Error: Unable to establish a database connection. Check username, password, and port.";
                }
            }
            
            if (connectionCreated) {
                outcome = "databaseList"; 
                ErrorMessage = null;
            }
            else {
                password = null;
                username = null;
                port = 5432;
            }
            FacesContext.getCurrentInstance();
            return outcome;        
        
    }
    
    //----------------------------------------------------------------------------------------
    
    public String conDatabase(String dbName) throws SQLException, IOException{
         
        boolean connectionCreated = false;
        String outcome = null;
        setSelectedDatabaseName(dbName);
        try {
            Class.forName("org.postgresql.Driver");
            Connection cnx = DriverManager.getConnection(url+port+"/"+dbName,username, password);
            PreparedStatement pstmt = cnx.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
            ResultSet rs = pstmt.executeQuery();
            
            databaseTables = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString(1));
                databaseTables.add(rs.getString(1));
            }
            connectionCreated = true;
            filteredTablesNames = new ArrayList<>();
            setFilteredTablesNames(databaseTables);
            
            System.out.println("connect");
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erreur");
            }
            
            if (connectionCreated) {
                outcome = "databaseTablesList";
            }
            FacesContext.getCurrentInstance();
            return outcome;
                 
    }
    
    //----------------------------------------------------------------------------------------
    
    public String conTable(String tabName) throws SQLException, IOException{
         
        boolean connectionCreated = false;
        String outcome = null;
        setSelectedTabName(tabName);
        try {
            String selectedDb = getSelectedDatabaseName(); // Get the selected database name
            if (selectedDb == null || selectedDb.isEmpty()) {
            // Handle the case when no database is selected
            return null;
            }
            Class.forName("org.postgresql.Driver");
            Connection cnx = DriverManager.getConnection(url+port+"/"+ selectedDb,username, password);
            PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM " + tabName );
            ResultSet rs = pstmt.executeQuery();
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            tableDataList = new ArrayList<>();
            columnNames = new ArrayList<>();
            if (rs.next()) {
            List<String> firstRow = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnValue = rs.getString(i);
                columnNames.add(columnName);
                firstRow.add(columnValue); // Add column data to the first row
            }
            tableDataList.add(firstRow); // Add the first row to the tableDataList
        }

            while (rs.next()) {
            List<String> rowData = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = rs.getString(i);
                rowData.add(columnValue); // Add column data to the row
            }
            tableDataList.add(rowData); // Add the row to the tableDataList
        }
            connectionCreated = true;
            
            System.out.println("connect");
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionBean.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erreur");
            }
            
            if (connectionCreated) {
                outcome = "tableData";
            }
            FacesContext.getCurrentInstance();
            return outcome;
            
        
    }

    //----------------------------------------------------------------------------------------
    
    public void openExcel() throws SQLException, IOException, ClassNotFoundException, ParseException {

    String dbName = getSelectedDatabaseName();
    String tableName = getSelectedTabName();
    if (file == null) {
        // Handle the case where the file is not selected
        System.out.println("No file selected.");
        return;
    }
    rowsWithErrors = null;

    Class.forName("org.postgresql.Driver");
    Connection cnx = DriverManager.getConnection(url +port+ "/" + dbName, username, password);
    Statement stmt = cnx.createStatement();

    String fileName = file.getName();
    InputStream inputStream = file.getInputStream();
    Workbook workbook = new XSSFWorkbook(inputStream);
    Sheet sheet = workbook.getSheetAt(0);

    int rows = sheet.getLastRowNum();
    if (rows >= 1) {
        // Get column names and data types from the database table
        ResultSetMetaData metaData = null;
        try (ResultSet resultSet = cnx.prepareStatement("SELECT * FROM " + tableName + " LIMIT 0").executeQuery()) {
            metaData = resultSet.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        int numColumns = metaData.getColumnCount();

        String[] dbColumnNames = new String[numColumns - 1]; // Start from the second column
        int[] dbColumnTypes = new int[numColumns - 1]; // Start from the second column
        boolean[] dbColumnNullable = new boolean[numColumns - 1]; // Start from the second column

        for (int i = 2; i <= numColumns; i++) { // Start from the second column
            dbColumnNames[i - 2] = metaData.getColumnName(i); // Start from the second column
            dbColumnTypes[i - 2] = metaData.getColumnType(i); // Start from the second column
            dbColumnNullable[i - 2] = metaData.isNullable(i) == ResultSetMetaData.columnNullable;
        }

        String[] excelColumnNames = new String[numColumns];
        Vector<Integer> columnIndexes = new Vector<>();

        // Iterate over the first row (header) of the Excel sheet
        Row headerRow = sheet.getRow(0);
        int numCells = headerRow.getPhysicalNumberOfCells();
        for (int c = 0; c < numCells; c++) {
            Cell cell = headerRow.getCell(c);
            if (cell != null) {
                excelColumnNames[c] = cell.toString().trim();
            }
        }

        // Iterate over dbColumnNames and find their indexes in excelColumnNames
        for (String dbColumnName : dbColumnNames) {
            int index = -1;
            for (int i = 0; i < excelColumnNames.length; i++) {
                if (dbColumnName.equals(excelColumnNames[i])) {
                    index = i;
                    break;
                }
            }
            columnIndexes.add(index);
        }

        // Print the results
        System.out.println("dbColumnNames: " + Arrays.toString(dbColumnNames));
        System.out.println("excelColumnNames: " + Arrays.toString(excelColumnNames));
        System.out.println("columnIndexes: " + columnIndexes);
        System.out.println("dbColumnNullable: " + Arrays.toString(dbColumnNullable));

        // Prepare the first part of the INSERT statement using dbColumnNames
        StringBuilder insertQueryPrefix = new StringBuilder("INSERT INTO " + tableName + " (");
        int j = 0;
        for (int i = 0; i < dbColumnNames.length; i++) {
            if (i > 0) {
                insertQueryPrefix.append(", ");
            }
            j = i;
            if (columnIndexes.get(i) != -1) {
                insertQueryPrefix.append(dbColumnNames[i]);
            }

        }
        if (columnIndexes.get(j) == -1) {
            insertQueryPrefix.setLength(insertQueryPrefix.length() - 2);
        }

        insertQueryPrefix.append(") VALUES (");
        int k = 0;
        boolean insert = true;
        // Create a list to store rows with errors
        rowsWithErrors = new ArrayList<>();

        // Iterate over the rows in the Excel file (starting from the second row)
        String cellCont = null;
        for (int r = 1; r <= rows; r++) {
            Row row = sheet.getRow(r);
            StringBuilder insertQueryForRow = new StringBuilder(insertQueryPrefix);
            k = 0;
            insert = true;
            for (int columnIndex : columnIndexes) {
                if (columnIndex != -1) {
                    Cell cell = row.getCell(columnIndex);
                    
                    if (cell != null && !cell.toString().equals("")) {
                        Object cellValue;
                        int columnType = dbColumnTypes[k];
                        
                        // Check data type and extract cell value accordingly
                        System.out.println("im column type " + columnType );
                        System.out.println("im content of the cell " + cell.toString());
//                        System.out.println("java.sql.Types.BOOLEAN "+java.sql.Types.BOOLEAN);
//                        System.out.println("java.sql.Types.FLOAT "+java.sql.Types.FLOAT);
//                        System.out.println("java.sql.Types.DOUBLE "+java.sql.Types.DOUBLE);
                        if (columnType == java.sql.Types.INTEGER) {                           
                            if (cell.getCellType() == CellType.NUMERIC) {
                                
                                cellValue = cell.toString();
                                double valueAsDouble = Double.parseDouble((String) cellValue);
                                int value = (int) valueAsDouble;
                                cellCont = null;
                                cellCont = value + "";
                                
                            } else {
                                // Handle non-numeric cells gracefully (e.g., set them to null)
                                cellValue = null;
                                errMessages.add("Error: Non-numeric cell found in column " + (columnIndex + 1));
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.getPartialViewContext().getRenderIds().add("logPanel");
                                insert = false;
                                // Add the row to the list of rows with errors
                                Cell lastCell = row.createCell(row.getLastCellNum());
                                lastCell.setCellValue("Error: Non-numeric cell found in column " + (columnIndex + 1));
                                rowsWithErrors.add(row);
                            }
                            System.out.println("im content of the cell " + cellCont);
                        } else if (columnType == java.sql.Types.VARCHAR) {
                            if (cell.getCellType() == CellType.STRING) {
                                cellValue = cell.toString();
                                cellCont = null;
                                cellCont = cleanUpString(cellValue.toString());
                            } else {
                                // Handle non-numeric cells gracefully (e.g., set them to null)
                                cellValue = null;
                                errMessages.add("Error: Non-string cell found column " + (columnIndex + 1));
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.getPartialViewContext().getRenderIds().add("logPanel");
                                insert = false;
                                // Add the row to the list of rows with errors
                                Cell lastCell = row.createCell(row.getLastCellNum());
                                lastCell.setCellValue("Error: Non-string cell found column " + (columnIndex + 1));
                                rowsWithErrors.add(row);
                            }
                            System.out.println("im content of the cell " + cellCont);
                        } else if (columnType == 91) {
                                String is = cell.toString();
                                boolean d = isDate(is);
                                System.out.println("im content date " + d);
                                if (d) {

                                    XSSFCreationHelper creationHelper = (XSSFCreationHelper) workbook.getCreationHelper();
                                    CellStyle style = workbook.createCellStyle();
                                    style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd")); 
                                    cell.setCellStyle(style);

                                    java.util.Date dateValue = cell.getDateCellValue();
                                    cellValue = new java.sql.Date(dateValue.getTime()); // Convert to java.sql.Date


                                    cellCont = cellValue.toString();
                                    System.out.println("im content of the cell of date " + cellCont);
                                } else {
                                    cellValue = null;
                                    errMessages.add("Error: Non-Date cell found column " + (columnIndex + 1));
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.getPartialViewContext().getRenderIds().add("logPanel");
                                    insert = false;
                                    // Add the row to the list of rows with errors
                                    Cell lastCell = row.createCell(row.getLastCellNum());
                                    lastCell.setCellValue("Error: Non-Date cell found column " + (columnIndex + 1));
                                    rowsWithErrors.add(row);
                                }
                                System.out.println("im content of the cell " + cellCont);
                            } else if (columnType == 7) {
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    cellValue = cell.toString();
                                    double valueAsDouble = Double.parseDouble((String) cellValue);
                                    float value = (float) valueAsDouble;
                                    cellCont = null;
                                    cellCont = value + "";
                                } else {
                                    cellValue = null;
                                    errMessages.add("Error: Non-Float cell found column " + (columnIndex + 1));
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.getPartialViewContext().getRenderIds().add("logPanel");
                                    insert = false;
                                    // Add the row to the list of rows with errors
                                    Cell lastCell = row.createCell(row.getLastCellNum());
                                    lastCell.setCellValue("Error: Non-Float cell found column " + (columnIndex + 1));
                                    rowsWithErrors.add(row);
                                    
                                }
                                System.out.println("im content of the cell " + cellCont);
                            } else if (columnType == java.sql.Types.DOUBLE) {
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    cellValue = cell.toString();
                                    double valueAsDouble = Double.parseDouble((String) cellValue);
                                    cellCont = null;
                                    cellCont = valueAsDouble + "";
                                    
                                } else {
                                    cellValue = null;
                                    errMessages.add("Error: Non-double cell found column " + (columnIndex + 1));
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.getPartialViewContext().getRenderIds().add("logPanel");
                                    insert = false;
                                    // Add the row to the list of rows with errors
                                    Cell lastCell = row.createCell(row.getLastCellNum());
                                    lastCell.setCellValue("Error: Non-double cell found column " + (columnIndex + 1));
                                    rowsWithErrors.add(row);
                                    
                                }
                                System.out.println("im content of the cell " + cellCont);
                            } else if (columnType == -7) {
                                if (cell.getCellType() == CellType.BOOLEAN) {
                                    cellCont = cell.toString();                                                                    
                                } else {
                                    cellValue = null;
                                    errMessages.add("Error: Non-boolean cell found column " + (columnIndex + 1));
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.getPartialViewContext().getRenderIds().add("logPanel");
                                    insert = false;
                                    // Add the row to the list of rows with errors
                                    Cell lastCell = row.createCell(row.getLastCellNum());
                                    lastCell.setCellValue("Error: Non-boolean cell found column " + (columnIndex + 1));
                                    rowsWithErrors.add(row);
                                    
                                }
                                System.out.println("im content of the cell " + cellCont);
                            }
 
                        insertQueryForRow.append("'");
                        insertQueryForRow.append(cellCont);
                        cellCont = null;
                        insertQueryForRow.append("', ");
                    } else {                        
                        if (dbColumnNullable[k] == false) {
                            insert = false;
                            System.out.println("im dkhlt 1");
                            errMessages.add("column does not exist");
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.getPartialViewContext().getRenderIds().add("logPanel");
                            Cell lastCell = row.createCell(row.getLastCellNum());
                            lastCell.setCellValue("column does not exist");
                            rowsWithErrors.add(row);  
                            
                        } else {
                            insertQueryForRow.append("NULL, ");
                        }
                    }
                    
                } else {
                    System.out.println("im in 1");
                    if (dbColumnNullable[k] == false) {
                        System.out.println("im in 2");
                        String ErrMessage = "column does not exist";
                        System.out.println(ErrMessage);
                        return;
                    }
                        
                }
                k = k + 1;
            }

            // Remove the trailing ", " and add closing parenthesis to the SQL statement

            insertQueryForRow.setLength(insertQueryForRow.length() - 2);
            insertQueryForRow.append(");");
            System.out.println("SQL INSERT Statement for Row " + (r + 1) + ": " + insertQueryForRow);

            if (insert) {
                stmt.execute(insertQueryForRow.toString());
                stmt.execute("commit");
            }
        }

        // Store the rows with errors in a separate Excel workbook
        Workbook errorWorkbook = new XSSFWorkbook();
        Sheet errorSheet = errorWorkbook.createSheet("Errors");

        int rowIndex = 0;
        
        error = !rowsWithErrors.isEmpty();
        // Check if there are any rows with errors
        if (!rowsWithErrors.isEmpty()) {           
            // Create a new row for the header (first row of the original Excel file)
            Row headerErrRow = errorSheet.createRow(rowIndex++);

            // Iterate over the cells in the first row of the original Excel file and add them to the header row
            Row firstRow = sheet.getRow(0);
            for (int c = 0; c < numCells; c++) {
                Cell cell = headerErrRow.createCell(c);
                Cell originalCell = firstRow.getCell(c);
                if (originalCell != null) {
                    CellType cellType = originalCell.getCellType();
                    if (cellType == CellType.STRING) {
                        cell.setCellValue(originalCell.getStringCellValue());
                    } else if (cellType == CellType.NUMERIC) {
                        cell.setCellValue(originalCell.getNumericCellValue());
                    }
                }
            }
        }

        // Iterate over the rows in the Excel file (starting from the second row)
        for (Row errorRow : rowsWithErrors) {
            // Create a new row for each errorRow
            Row newRow = errorSheet.createRow(rowIndex++);
            for (int c = 0; c < numCells; c++) {
                Cell cell = newRow.createCell(c);
                Cell originalCell = errorRow.getCell(c);
                if (originalCell != null) {
                    CellType cellType = originalCell.getCellType();
                    if (cellType == CellType.STRING) {
                        cell.setCellValue(originalCell.getStringCellValue());
                    } else if (cellType == CellType.NUMERIC) {
                        cell.setCellValue(originalCell.getNumericCellValue());
                    }
                }
            }
        }

        // Save the error report workbook to a file
        FileOutputStream errorReportFile = new FileOutputStream("error_report.xlsx");
        errorWorkbook.write(errorReportFile);
        errorReportFile.close();

        // Close workbooks and streams
        workbook.close();
        inputStream.close();

        importButtonDisabled = true;
        conTable(tableName);

    }
}
    
    //----------------------------------------------------------------------------------------

    public void downloadErrorReport() throws IOException {
    String errorReportPath = "error_report.xlsx";
    String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.responseComplete();

    InputStream stream = new FileInputStream(errorReportPath);

    facesContext.getExternalContext().responseReset();
    facesContext.getExternalContext().setResponseContentType(contentType);
    facesContext.getExternalContext().setResponseHeader("Content-Disposition", "attachment; filename=\"error_report.xlsx\"");

    byte[] buffer = new byte[1024];
    int length;
    while ((length = stream.read(buffer)) > 0) {
        facesContext.getExternalContext().getResponseOutputStream().write(buffer, 0, length);
    }
    stream.close();

    facesContext.responseComplete();
   
}

    //----------------------------------------------------------------------------------------
    
    public String performSearchDb() {
    List<String> filteredNames = new ArrayList<>();
    for (String dbName : databaseNames) {
        if (dbName.toLowerCase().contains(searchKeyword.toLowerCase())) {
            filteredNames.add(dbName);
        }
    }
    setFilteredDatabaseNames(filteredNames);
    return null;
}

    //----------------------------------------------------------------------------------------
    
    public String performSearchTab() {
        List<String> filteredNames = new ArrayList<>();
        for (String tabName : databaseTables) {
            if (tabName.toLowerCase().contains(searchKeyword.toLowerCase())) {
                filteredNames.add(tabName);
            }
        }
        setFilteredTablesNames(filteredNames);
        return null;
    }
    
    //----------------------------------------------------------------------------------------

    public static String cleanUpString(String input) {
        // Replace multiple spaces with a single space
        String cleanedUp = input.replaceAll("\\s+", " ");
        // Trim leading and trailing spaces
        cleanedUp = cleanedUp.trim();
        return cleanedUp;
    }
    
    //----------------------------------------------------------------------------------------

    public static boolean isDate(String dateStr) {
        boolean format1 = true;
        boolean format2 = true;
    try {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
 
        sdf1.setLenient(false);
        java.util.Date date1 = sdf1.parse(dateStr);
    } catch (ParseException e) {
        format1 = false;
    }
    
    try {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MMM-dd");       
        sdf2.setLenient(false);
        java.util.Date date2 = sdf2.parse(dateStr);
    } catch (ParseException e) {
        format2 = false;
    }
    
    return format1 || format2;
}
    
    public void clearLog() {
        errMessages.clear();
    }
    
    
    public String BacktodbTablist() throws SQLException, IOException {
        
        clearLog();
        error = false;
        
        FacesContext.getCurrentInstance();
            return "databaseTablesList";
    }
    
    
    public String Backtodblist() throws SQLException, IOException {

        FacesContext.getCurrentInstance();
            return "databaseList";
    }
}



