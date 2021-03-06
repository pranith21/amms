package com.iq.amms.services.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.iq.service.helpers.SystemConf;
import org.iq.util.date.DateUtil;
import org.iq.util.date.DateUtil.DateFormat;
import org.iq.util.file.FileUtil;
import org.iq.util.money.Money;
import org.iq.util.money.MoneyUtil;
import org.iq.util.string.StringUtil;

import com.iq.amms.Constants.PaymentMode;
import com.iq.amms.valueobjects.BillDetailsVO;
import com.iq.amms.valueobjects.BillPdflDataVO;
import com.iq.amms.valueobjects.ReportDataVO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfBuilder {

  private static final Font INFO_FONT = new Font(Font.FontFamily.HELVETICA, 4,
	  Font.BOLD, BaseColor.BLACK);
/*  private static final Font HEADER_BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10,
      Font.BOLD, BaseColor.BLACK);
  private static final Font HEADER_NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10,
      Font.NORMAL, BaseColor.BLACK);
  private static final Font HEADER_ITALIC_FONT = new Font(Font.FontFamily.HELVETICA, 10,
      Font.ITALIC, BaseColor.BLACK);*/
  private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 8,
      Font.BOLD, BaseColor.BLACK);
  private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA,
	  8, Font.NORMAL, BaseColor.BLACK);
  private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 7,
	  Font.NORMAL, BaseColor.BLACK);

  /**
   * @param fileName
   * @param billPdflDataVO
   * @throws Exception
   */
  public static void createInvoice(String fileName,
      BillPdflDataVO billPdflDataVO)
  throws Exception {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(fileName));
    document.open();

    addMetaData(document, "Maintenance Bill");
//  short count = 0;
    addHeader(document, billPdflDataVO.getDateOfGeneration());
    addEmptyLine(document, 1);

    addMasterData(document, billPdflDataVO.getName(), billPdflDataVO
        .getFlatNumber(), billPdflDataVO.getDateOfGeneration(),
        billPdflDataVO.getBillId(), billPdflDataVO.getTotalAmount(),
        billPdflDataVO.getDateOfDue());
    addEmptyLine(document, 1);

    addDetailsData(document, billPdflDataVO);
    addEmptyLine(document, 1);

    addFooter(document, billPdflDataVO);
    addEmptyLine(document, 1);

    document.close();
  }
  
  /**
   * @param string
   * @param billPdflDataVOList
   * @throws Exception 
   */
  public static void createConsolidatedInvoice(String fileName,
      ArrayList<BillPdflDataVO> billPdflDataVOList) throws Exception {
    
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(fileName));
    document.open();

    addMetaData(document, "Maintenance Bill");
    short count = 0;
    for (BillPdflDataVO billPdflDataVO : billPdflDataVOList) {
      addHeader(document, billPdflDataVO.getDateOfGeneration());
      addEmptyLine(document, 1);

      addMasterData(document, billPdflDataVO.getName(), billPdflDataVO
          .getFlatNumber(), billPdflDataVO.getDateOfGeneration(),
          billPdflDataVO.getBillId(), billPdflDataVO.getTotalAmount(),
          billPdflDataVO.getDateOfDue());
      addEmptyLine(document, 1);

      addDetailsData(document, billPdflDataVO);
      addEmptyLine(document, 1);

      addFooter(document, billPdflDataVO);
      addEmptyLine(document, 1);

      count++;
      if (count == 3) {
        document.newPage();
        count = 0;
      }
      else {
        addSeparator(document);
        addEmptyLine(document, 1);
      }
    }
    document.close();
  }
  
  private static void addHeader(Document document, Date dateOfGeneration)
      throws DocumentException {
    Paragraph headerParagraph =
        new Paragraph("MGRA Maintenance Bill, for the month of "
            + DateUtil.dateToString(dateOfGeneration, DateFormat.MMM_yy),
            BOLD_FONT);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    document.add(headerParagraph);
  }

  private static void addMasterData(Document document, String name,
      String fullFlatNumber, Date dateOfGeneration, String invoiceNumber,
      Double totalAmount, Date dueDate) throws DocumentException {
    PdfPTable table = new PdfPTable(new float[] { 10, 60, 10, 20 });
    table.getDefaultCell().setBorder(0);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

    table.addCell(new Phrase("Name", SMALL_FONT));
    table.addCell(new Phrase(name, NORMAL_FONT));

    table.addCell(new Phrase("Date", SMALL_FONT));
    table.addCell(new Phrase(DateUtil.dateToString(dateOfGeneration,
        DateFormat.MMM_dd_yyyy), NORMAL_FONT));

    table.addCell(new Phrase("Apt. no.", SMALL_FONT));
    table.addCell(new Phrase(fullFlatNumber, NORMAL_FONT));

    table.addCell(new Phrase("Amount Due", SMALL_FONT));
    table.addCell(new Phrase(String.format("%1.2f",totalAmount), NORMAL_FONT));

    table.addCell(new Phrase("Inv. no.", SMALL_FONT));
    table.addCell(new Phrase(invoiceNumber, NORMAL_FONT));

    table.addCell(new Phrase("Due Date", SMALL_FONT));
    table.addCell(new Phrase(DateUtil.dateToString(dueDate,
        DateFormat.MMM_dd_yyyy), NORMAL_FONT));

    document.add(table);
  }

  private static void addDetailsData(Document document, BillPdflDataVO billPdflDataVO)
	  throws Exception {
    
	PdfPTable table = new PdfPTable(new float[] { 80, 20 });
	table.getDefaultCell().setBorder(Rectangle.BOX);
	table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
	table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
	table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

	/*
	 * PdfPCell headerCell = new PdfPCell(new Phrase("Particulars",BOLD_FONT));
	 * headerCell.setBorder(Rectangle.BOX);
	 * headerCell.setBorderColor(BaseColor.LIGHT_GRAY);
	 * headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * table.addCell(headerCell);
	 * 
	 * headerCell = new PdfPCell(new Phrase("Amount (Rs.)",BOLD_FONT));
	 * headerCell.setBorder(Rectangle.BOX);
	 * headerCell.setBorderColor(BaseColor.LIGHT_GRAY);
	 * headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * table.addCell(headerCell);
	 */

//    table.addCell(new Phrase("Particulars", BOLD_FONT));
//    table.addCell(new Phrase("Amount (Rs.)", BOLD_FONT));
    
    PdfPCell detailCell = new PdfPCell(new Phrase("Particulars", BOLD_FONT));
    detailCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
    table.addCell(detailCell);

    detailCell = new PdfPCell(new Phrase("Amount (Rs.)", BOLD_FONT));
    detailCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
    table.addCell(detailCell);


    short count = 0;
    ArrayList<BillDetailsVO> billDetailsVOList = billPdflDataVO.getBillDetailsList();
    for (BillDetailsVO detailsVO : billDetailsVOList) {
//      table.addCell(new Phrase(detailsVO.getChargeName(), NORMAL_FONT));
      detailCell = new PdfPCell(new Phrase(detailsVO.getChargeName(), NORMAL_FONT));
      detailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
      detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
      table.addCell(detailCell);
      
//      table.addCell(new Phrase(StringUtil.getStringForForm(detailsVO.getChargeAmount()), NORMAL_FONT));
      detailCell = new PdfPCell(new Phrase(String.format("%1.2f",detailsVO.getChargeAmount()), NORMAL_FONT));
      detailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
      table.addCell(detailCell);
      
      count++;
    }

    for (int i = count; i < 6; i++) {
      table.addCell(new Phrase(" ", NORMAL_FONT));
      table.addCell(new Phrase(" ", NORMAL_FONT));
    }
	
//	table.addCell(new Phrase("Total ->", BOLD_FONT));
    detailCell = new PdfPCell(new Phrase("Total", BOLD_FONT));
    detailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
    table.addCell(detailCell);
    
//	table.addCell(new Phrase(StringUtil.getStringForForm(billPdflDataVO.getTotalAmount()), BOLD_FONT));
    detailCell = new PdfPCell(new Phrase(String.format("%1.2f",billPdflDataVO.getTotalAmount()), BOLD_FONT));
    detailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    detailCell.setBorderColor(BaseColor.LIGHT_GRAY);
    table.addCell(detailCell);
    
	document.add(table);

	addEmptyLine(document, 1);

	PdfPTable inWordsTable = new PdfPTable(new float[] { 20, 80 });
	inWordsTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
	inWordsTable.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
	inWordsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
	inWordsTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

    Double amount = billPdflDataVO.getTotalAmount();
    String amountStr = StringUtil.getStringValue(amount);
    String[] amountParts = amountStr.split("\\.");
    Integer amountInt = Integer.valueOf(amountParts[0]+StringUtil.padCharTrail(amountParts[1], 2, "0").substring(0, 2));
    Money money = new Money();
    money.setAmount(amountInt);             

	inWordsTable.addCell(new Phrase("Amount Due in words", SMALL_FONT));
	inWordsTable.addCell(new Phrase(MoneyUtil.getMoneyInWords(money),
        BOLD_FONT));

	document.add(inWordsTable);
  }

  private static void addFooter(Document document, BillPdflDataVO billPdflDataVO) throws DocumentException {
	PdfPTable table = new PdfPTable(new float[] { 80, 20 });

	PdfPCell footCell = new PdfPCell(new Phrase("", SMALL_FONT));
	footCell.setBorder(Rectangle.NO_BORDER);
	footCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	table.addCell(footCell);

    footCell = new PdfPCell(new Phrase("Facility Manager", NORMAL_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(footCell);

    footCell = new PdfPCell(new Phrase(billPdflDataVO.getBillId(), INFO_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(footCell);

    footCell = new PdfPCell(new Phrase("E. & O.E.", INFO_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(footCell);

	document.add(table);
  }

  private static void addSeparator(Document document) throws DocumentException {
	PdfPTable table = new PdfPTable(new float[] { 100 });

	PdfPCell footCell = new PdfPCell(
		new Phrase(
			"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
			SMALL_FONT));
	footCell.setBorder(Rectangle.NO_BORDER);
	footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(footCell);

	document.add(table);
  }

  /**
   * @param document
   * @param number
   * @throws DocumentException
   */
  private static void addEmptyLine(Document document, int number)
	  throws DocumentException {
	for (int i = 0; i < number; i++) {
	  document.add(new Paragraph(" ", SMALL_FONT));
	}
  }

  /**
   * @param fileName
   * @param billPdflDataVO
   * @throws Exception
   */
  public static void createReceipt(String fileName, BillPdflDataVO billPdflDataVO) throws Exception {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(fileName));
    document.open();

    addMetaData(document, "Payment Receipt");
    
    addReceiptHeader(document, new Date());
    
    addEmptyLine(document, 1);

    addReceiptMasterData(document, billPdflDataVO.getName(),
        billPdflDataVO.getFlatNumber(),
        billPdflDataVO.getDateOfGeneration(),
        billPdflDataVO.getReceiptNumber(), billPdflDataVO.getPaymentMode(),
        billPdflDataVO.getPaymentNumber(),
        billPdflDataVO.getPaymentStatus(), billPdflDataVO.getTotalAmount());
    
    addEmptyLine(document, 2);

    addReceiptFooter(document);

    document.close();
  }
  
  private static void addReceiptHeader(Document document,
      Date dateOfGeneration) throws DocumentException {
    Paragraph headerParagraph =
        new Paragraph("MGRA Payment Receipt", BOLD_FONT);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    document.add(headerParagraph);
  }
  
  private static void addReceiptMasterData(Document document, String name,
      String fullFlatNumber, Date dateOfGeneration, String receiptNumber,
      PaymentMode modeOfPayment, String paymentNumber, String paymentStatus, Double totalAmount)
      throws Exception {
    PdfPTable dateTable = new PdfPTable(new float[] { 80, 5, 15 });
    
    dateTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    dateTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    dateTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);

    dateTable.addCell(new Phrase(" ", NORMAL_FONT));

    PdfPCell tableCell = new PdfPCell(new Phrase("Date", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    dateTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(DateUtil.dateToString(dateOfGeneration,
        DateFormat.MMM_dd_yyyy), NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    dateTable.addCell(tableCell);

    document.add(dateTable);

    addEmptyLine(document, 1);

    PdfPTable detailsTable = new PdfPTable(new float[] { 15, 85 });
    detailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    detailsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    detailsTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);

    tableCell = new PdfPCell(new Phrase("Receipt number", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(receiptNumber, NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    detailsTable.addCell(new Phrase(" ", SMALL_FONT));
    detailsTable.addCell(new Phrase(" ", NORMAL_FONT));

    tableCell = new PdfPCell(new Phrase("Received from", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(name, NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase("Apartment number", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(fullFlatNumber, NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    detailsTable.addCell(new Phrase(" ", NORMAL_FONT));
    detailsTable.addCell(new Phrase(" ", NORMAL_FONT));

    String amountStr = StringUtil.getStringValue(totalAmount);
    String[] amountParts = amountStr.split("\\.");
    Integer amountInt =
        Integer.valueOf(amountParts[0]
            + StringUtil.padCharTrail(amountParts[1], 2, "0")
                .substring(0, 2));
    Money money = new Money();
    money.setAmount(amountInt);
    
    tableCell = new PdfPCell(new Phrase("Received Amount", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(String.format("%1.2f",totalAmount), NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase("Amount in words", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(MoneyUtil.getMoneyInWords(money), NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    detailsTable.addCell(new Phrase(" ", SMALL_FONT));
    detailsTable.addCell(new Phrase(" ", NORMAL_FONT));

    tableCell = new PdfPCell(new Phrase("Mode of payment", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(modeOfPayment.toString(), NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    switch (modeOfPayment) {
      case CASH:
        detailsTable.addCell(new Phrase(" ", SMALL_FONT));
        detailsTable.addCell(new Phrase(" ", NORMAL_FONT));
        break;
      case CHEQUE:
        tableCell = new PdfPCell(new Phrase("Cheque Number", SMALL_FONT));
        tableCell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase(paymentNumber, NORMAL_FONT));
        tableCell.setBorder(Rectangle.BOTTOM);
        tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
        detailsTable.addCell(tableCell);
        break;
      case NEFT:
          tableCell = new PdfPCell(new Phrase("Transaction Id", SMALL_FONT));
          tableCell.setBorder(Rectangle.NO_BORDER);
          detailsTable.addCell(tableCell);

          tableCell = new PdfPCell(new Phrase(paymentNumber, NORMAL_FONT));
          tableCell.setBorder(Rectangle.BOTTOM);
          tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
          detailsTable.addCell(tableCell);
          break;
      case ERROR_RECTIFICATION:
      case DISCOUNT:
          tableCell = new PdfPCell(new Phrase("Comments", SMALL_FONT));
          tableCell.setBorder(Rectangle.NO_BORDER);
          detailsTable.addCell(tableCell);

          tableCell = new PdfPCell(new Phrase(paymentNumber, NORMAL_FONT));
          tableCell.setBorder(Rectangle.BOTTOM);
          tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
          detailsTable.addCell(tableCell);
          break;
      default:
        detailsTable.addCell(new Phrase(" ", SMALL_FONT));
        detailsTable.addCell(new Phrase(" ", NORMAL_FONT));
        break;
    }
    
    tableCell = new PdfPCell(new Phrase("Payment Status", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(paymentStatus, NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    detailsTable.addCell(tableCell);

    
    document.add(detailsTable);

  }

  private static void addReceiptFooter(Document document) throws DocumentException {
    PdfPTable table = new PdfPTable(new float[] { 80, 20 });

    PdfPCell footCell = new PdfPCell(new Phrase("", SMALL_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(footCell);

    footCell = new PdfPCell(new Phrase("Facility Manager", NORMAL_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(footCell);

    footCell = new PdfPCell(new Phrase("", SMALL_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(footCell);

    footCell = new PdfPCell(new Phrase("E. & O.E.", INFO_FONT));
    footCell.setBorder(Rectangle.NO_BORDER);
    footCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(footCell);

    document.add(table);
  }
  
  public static void createReport(String fileName, ReportDataVO reportDataVO) throws Exception {
	    Document document = new Document();
	    PdfWriter.getInstance(document, new FileOutputStream(fileName));
	    document.open();

	    addMetaData(document, "Report - "+reportDataVO.getTableHeader());
	    
	    addReportHeader(document, reportDataVO.getTableHeader());
	    
	    addEmptyLine(document, 1);

	    addReportMasterData(document, reportDataVO);
	    
	    addEmptyLine(document, 2);

	    addReceiptFooter(document);

	    document.close();
	  
  }
  
  /**
   * @param document
   */
  private static void addMetaData(Document document, String title) {
    document.addTitle(title);
    document.addAuthor("AMMS System");
    document.addCreationDate();
  }

  private static void addReportHeader(Document document, String title) throws DocumentException {
    Paragraph headerParagraph =
        new Paragraph("Report - "+title, BOLD_FONT);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    document.add(headerParagraph);
    
    PdfPTable dateTable = new PdfPTable(new float[] { 80, 5, 15 });
    
    dateTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    dateTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    dateTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);

    dateTable.addCell(new Phrase(" ", NORMAL_FONT));

    PdfPCell tableCell = new PdfPCell(new Phrase("Date", SMALL_FONT));
    tableCell.setBorder(Rectangle.NO_BORDER);
    dateTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(DateUtil.dateToString(new Date(),
        DateFormat.MMM_dd_yyyy), NORMAL_FONT));
    tableCell.setBorder(Rectangle.BOTTOM);
    tableCell.setBorderColor(BaseColor.LIGHT_GRAY);
    dateTable.addCell(tableCell);

    document.add(dateTable);

    addEmptyLine(document, 1);

  }
  
  private static void addReportMasterData(Document document, ReportDataVO reportDataVO)
      throws Exception {

    PdfPTable detailsTable = new PdfPTable(3);
    detailsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    detailsTable.getDefaultCell().setBorder(Element.PTABLE);
    detailsTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
    
    String[] oneRow = reportDataVO.getHeaders();

    PdfPCell tableCell = new PdfPCell(new Phrase(oneRow[0], BOLD_FONT));
    tableCell.setColspan(1);
    tableCell.setBorder(Rectangle.BOX);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(oneRow[1], BOLD_FONT));
    tableCell.setColspan(1);
    tableCell.setBorder(Rectangle.BOX);
    detailsTable.addCell(tableCell);

    tableCell = new PdfPCell(new Phrase(oneRow[2], BOLD_FONT));
    tableCell.setColspan(1);
    tableCell.setBorder(Rectangle.BOX);
    detailsTable.addCell(tableCell);

    ArrayList<String[]> data = reportDataVO.getData();
    
    int size = data.size();
    
    if(data !=null && size > 0){
    	for (String[] content : data) {
    	    size--;
    	    tableCell = new PdfPCell(new Phrase(content[0], SMALL_FONT));
    	    tableCell.setColspan(1);
    	    tableCell.setBorder(Rectangle.BOX);
    	    detailsTable.addCell(tableCell);

    	    tableCell = new PdfPCell(new Phrase(content[1], SMALL_FONT));
    	    tableCell.setColspan(1);
    	    tableCell.setBorder(Rectangle.BOX);
    	    detailsTable.addCell(tableCell);
    	    if(size == 0){
        	    String c = content[2];
        	    System.out.println("cCCCCCCCCCCCCCCCCCCCCCCCC##################"+c);
        	    c= c.substring(3,c.length()-4);
        	    tableCell = new PdfPCell(new Phrase(c, SMALL_FONT));
    	    }else{
        	    tableCell = new PdfPCell(new Phrase(content[2], SMALL_FONT));
    	    }
    	    tableCell.setColspan(1);
    	    tableCell.setBorder(Rectangle.BOX);
    	    detailsTable.addCell(tableCell);
		}
    }
    document.add(detailsTable);

  }
  
  public static void main(String[] args) throws Exception {
		ReportDataVO reportDataVO = new ReportDataVO();
		reportDataVO.setTableHeader("Hello");
		String[] oneRow = new String[]{ "Flat Number", "Owner",
		          "<b>Current Due</b>" };
		reportDataVO.setHeaders(oneRow);
		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(oneRow);
		reportDataVO.setData(data);
	    FileUtil.createFolder(SystemConf.getAppRoot()+File.separator+"pdfs/reports");
	    PdfBuilder.createReport(SystemConf.getAppRoot()+File.separator+"pdfs/reports"+
	    File.separator+reportDataVO.getTableHeader()+".pdf", reportDataVO);
  }
}