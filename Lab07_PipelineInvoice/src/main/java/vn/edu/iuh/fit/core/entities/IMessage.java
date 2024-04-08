package vn.edu.iuh.fit.core.entities;

import vn.edu.iuh.fit.core.InvoiceInfo;

public interface IMessage {
    InvoiceInfo getInvoiceInfo();
    void setInvoiceInfo(InvoiceInfo invoiceInfo);
    String getFileContent();
    void setFileContent(String fileContent);
}
