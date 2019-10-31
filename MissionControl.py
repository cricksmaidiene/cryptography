import os
print("Mission Control Dashboard for MTD Sales and Stock Reporting")
print("Press Enter to Begin")
input()
import RenameFiles
input()
import OriginalI50Values
print("UP NEXT->CONVERTING DISTRIBUTOR FILES TO FLAT FILES")

input()
print("Launching 3 Gears Macro, PRESS ENTER to run InitialSteps Macro")
input()
import ThreeGearsMacroScript
print("3 Gears complete, PRESS ENTER to get FLAT sums from I-50 and Stock")
input()
import OriginalFLATSum
import OriginalSTOCKSum
print("PRESS ENTER TO CONTINUE")
input()
while True:
    print("Input D to open flat files, else Input Q to skip. Press Enter when selection is made")
    choice = input().lower().rstrip()
    if choice=="q":
        break
    elif choice=="d":
        os.system('start EXCEL.EXE "C:/Databases/I50.xls"')
        os.system('start EXCEL.EXE "C:/Databases/Stock.xls"')
        print("Launching Microsoft Excel")
        print("Files launching: 1. I50 and 2. Stock Flat files")
        print("Note the totals in the Flat Files and then close i50 flat & Stock Flat")
        print("PRESS ENTER WHEN READY")
        input()
        break
    else:
        print("Invalid choice, please choose again\n")

print("UP NEXT->Finding Number of Rows in Flat Files")
print("PRESS ENTER WHEN READY")

input()
import NumberOfRowsInFlats
print("UP NEXT->Deleting Previously Generated Queries from C:/ProgramData and C:/Databases")
print("PRESS ENTER WHEN READY")

input()
import DelQueriesScript
print("UP NEXT->Exporting Customers as cust.csv")
print("PRESS ENTER WHEN READY")

input()
import ExportCustomersScript
print("UP NEXT->Delete i50 Procedure in DB")
print("PRESS ENTER WHEN READY")

input()
import DelI50Script
print("UP NEXT->Checking For New Customers, 'Connect to DB Macro'")
print("PRESS ENTER WHEN READY")

input()
import ConnectToDbMacroScript
print("WATCH FOR PROMPT TO SAVE i50.xls: PRESS ENTER AFTER CLICKING SAVE IN DIALOG (check alt-tab), to add new customers")
input()
print("Launching I50.xls, see if new customers need to be added, close I50 and press enter")
print("When asked to save, click Yes and choose Proprietary")
os.system('start EXCEL.EXE "C:/Databases/I50.xls"')

input()
while True:
    print("Input N to add new customers else Input Q to skip. Press Enter when selection is made")
    choice = input().lower().rstrip()
    if choice=="q":
        break
    elif choice=="n":
        print("Waiting for user to add and update new customers, press enter when new customers are added")
        os.system('start EXCEL.EXE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Queries/cust.csv"')
        os.system('start EXCEL.EXE "C:/Databases/dic_customers format.xlsx"')
        os.system('start EXCEL.EXE "C:/Databases/CTC/Revised/Updated/MTD Sales.xlsx"')
        os.system('start EXCEL.EXE "C:/Databases/I50.xls"')
        print("Launching Microsoft Excel")
        print("Files launching: 1. I50.xls 2.Cust.csv 3.DIC_CUST Format 4.MTD Sales files")
        print("PRESS ENTER WHEN READY")
        input()
        break
    else:
        print("Invalid choice, please choose again\n")

print("UP NEXT->Converting FLATS to CSVs")
print("PRESS ENTER WHEN READY")

input()
import ConvertToCSVMacroScript
print("Add current month's timestamp and close:")
print("Launching Microsoft Excel, press enter when timestamps are ready")
os.system('start EXCEL.EXE "C:/Databases/i50 temp/i50salesweekly.csv"')
input()
print("UP NEXT->Finding Number of Rows in CSV Files")
print("PRESS ENTER WHEN READY")

input()
import NumberOfRowsInCSVs
print("WARNING Press ENTER ONLY AFTER Table Data Import Wizard in mySQL DB is Complete")
print("UP NEXT->Exporting Queries")

input()
print("Press Enter to Start Exporting Queries")
input()
import ExportQueriesScript
print("UP NEXT->Copying Queries from C:/ProgramData to C:/Databases")
print("PRESS ENTER WHEN READY")

input()
import QueryCopyScript
print("UP NEXT->Compile Macro (with Qry Gen)")
print("PRESS ENTER WHEN READY")

input()
import CompileScript
print("UP NEXT->Autofit Reports for Aesthetics")
print("PRESS ENTER WHEN READY")

input()
import ReportAestheticsScript
print("UP NEXT->Updating DMP, PvSC and MTD Files")
print("PRESS ENTER WHEN READY")

input()
import ThreeFilesScript
print("UP NEXT->Mail Creation")
print("PRESS ENTER WHEN READY")

input()
import MailCreationScript
print("Watch for Dialog Box to save Mail.xlsm, any of the option would be fine to select")
print("Next: Filtered Products based on BU")
print("PRESS ENTER WHEN READY")

input()
print("Launching Microsoft Excel")
os.system('start EXCEL.EXE "C:/Databases/Steps/DMPFilter.xlsm"')
print("click on run everything")
print("Complete. Copy the Sheets into the Mail and Send")

