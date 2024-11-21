

# sunny_apps Local bespoke apps
No longer maintained. See BICA. 
LKCBooking (Liberton Kirk Centre Booking) is a bespoke app of activity halls booking for Liberton Kirk Church, Edinburgh. Primary Booking is performed online hosted by a web-company. The company provides only booking form, hold data, and export the booking data as excel files to the enduser. There is no VPN or webservice API for user to access data. LKCBooking takes the exported excel files as data source to generat contract form and invoice to clients.

LKCBooking is a standalone Java apps using swing as GUI, apache-poi for excel file parse, and apache-pdfbox to generate PDF file. It can sort and select dataset. The selected dataset are re-grouped according to the combination of title and user name, which is usually made of a contract or invoice, i.e one contract or invoice may involve multiple bookings. 

Workflow as:
1. load excel file using JFileChooser. It will display data in GUI table and status bar show how many records and date.
2. Sort the table data according to user's purposes and then select data to generate PDF file, it can be single, multiple and block selection. 
3. A dialog box display the selected data in group, the select the group to generate contract and invoice in PDF file.      

This Eclipse IDE project, you can clone the project and import to Eclipse IDE. If uer other IDE, you may need to configure the dependencies (classpath/modulepath). There two main functional classes: XlsxExtractor for extracting .xlsx file data input, if anything input data being changed, the class should be modified; PdfGenerator for generate the output PDF file. The Data flow as excel data to map row due to sometimes the exported excel file had different number of columns(bug?), then to table-data for display and selection,  finally group selected data to a MediaData object for generate pdf file.      
