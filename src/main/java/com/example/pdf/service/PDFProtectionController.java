package com.example.pdf.service;

import com.example.pdf.service.PDFProtectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PDFProtectionController {

    @Autowired
    private PDFProtectionService pdfProtectionService;

    @PostMapping("/protect")
    public String protectPDF(
            @RequestParam("inputPDFPath") String inputPDFPath,
            @RequestParam("outputPDFPath") String outputPDFPath,
            @RequestParam("password") String password) {
        try {
            // Call the service method to protect the PDF
            pdfProtectionService.protectPDF(inputPDFPath, outputPDFPath, password);
            return "PDF protected successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while protecting PDF.";
        }
    }
}
