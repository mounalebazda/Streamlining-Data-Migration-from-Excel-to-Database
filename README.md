# Title :

DataSync Web App: Streamlining Data Migration from Excel to Database

# Description : 

DataSync is an advanced web application developed during an internship at SONELGAZ's electricity production branch in Algiers from September 2023 to December 2023, aimed at optimizing the process of migrating data from Excel spreadsheets to structured databases. The application addresses common challenges such as data type mismatches, incorrect column order, and manual data entry errors. By automating data validation and correction prior to database integration, DataSync ensures precision and efficiency, significantly reducing error rates and improving data integrity.

# Technologies Used :

- JavaServer Faces (JSF): Utilized as the primary framework for building the application's interface, ensuring a robust and manageable server-side user interface.
- PostgreSQL: Serves as the backend database, providing a powerful and scalable solution for data storage and management.
- Java EE: Used for backend logic, leveraging enterprise-level capabilities for application development.
- Payara Server: An application server derived from GlassFish Server Open Source Edition, providing a reliable and secure platform for deploying the application.
- Apache POI: Employed for handling Excel data interactions, allowing for effective reading and writing to Excel files within the Java environment.
- HTML/CSS: Used for structuring and styling the application's web interface.

# Key Features and Functionalities : 

- Database Connectivity: DataSync allows users to securely connect to their database servers using credentials. It provides a seamless interface to visualize and interact with the tables of each database.
- Selective Data Integration: Users can select specific tables within a database to which Excel data should be added, offering targeted data integration.
- Automated Data Validation: Before integrating data into the database, DataSync performs automated checks to validate data against the database schema. This includes verifying data types, column order, and ensuring that the data meets the required constraints of the database table.
- Error Detection and Reporting: If discrepancies or errors are detected during the data validation process, DataSync not only identifies the problematic rows but also specifies the exact columns where errors occurred. This precision helps users quickly locate and address data integrity issues.
- Error Correction Workflow: DataSync generates a new Excel file containing only the rows with errors, clearly marking the columns needing corrections. Users can then make necessary modifications offline and re-upload the corrected file to the database, ensuring that only accurate data is integrated.


# Benefits : 

By automating crucial aspects of data validation and correction, DataSync significantly reduces error rates and enhances data integrity, ensuring efficient and precise updates. Its user-friendly interface simplifies complex data migration tasks, making it an invaluable tool for database administrators and data managers looking to streamline database updates.


# Target Audience : 

- Database Administrators and Data Managers: For professionals managing large datasets who need robust tools to facilitate efficient data migration and maintenance.
- IT Departments: Suitable for IT personnel looking for powerful solutions to enhance data handling and database management practices within their organizations.
- Business Analysts and Data Analysts: Analysts who regularly interact with large volumes of data and require efficient tools to ensure data accuracy and reliability.
