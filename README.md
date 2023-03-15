# sunny_apps Local bespoke apps

LKCBooking (Liberton Kirk Centre Booking) is a bespoke app of activity halls booking for Liberton Kirk Church, Edinburgh. Primary Booking is performed online hosted by a web-company. The company provides only booking form, hold data, and export the booking data as excel files to the enduser. There is no VPN or webservice API for user to access data. LKCBooking takes the exported excel files as data source to generat contract form and invoice to clients.

LKCBooking is a Java apps using swing as GUI, apache-poi for excel file parse, and apache-pdfbox to generate PDF file. It can sort and select dataset. The selected dataset are re-grouped according to the combination of title and user name, which is usually made of a contract or invoice, i.e one contract or invoice may involve multiple bookings. 

Workflow as:
1. load excel file using JFileChooser. It will display data in GUI table and status bar show how many records and date.
2. Sort the table data according to user's purposes and then select data to generate PDF file, it can be single, multiple and block selection. 
3. A dialog box display the selected data in group, the select the group to generate contract and invoice in PDF file.      
