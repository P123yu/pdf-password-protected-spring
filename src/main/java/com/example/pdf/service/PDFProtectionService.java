////package com.example.pdf.service;
////
////import org.apache.pdfbox.pdmodel.PDDocument;
////import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
////import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
////import org.springframework.stereotype.Service;
////
////import java.io.File;
////import java.io.IOException;
////
////@Service
////public class PDFProtectionService {
////
////    public void protectPDF(String inputPDFPath, String outputPDFPath, String password) throws IOException {
////        // Check if input file exists
////        File inputFile = new File(inputPDFPath);
////        if (!inputFile.exists()) {
////            throw new IOException("Input file does not exist at the specified path: " + inputPDFPath);
////        }
////
////        // Load the PDF document from the file
////        PDDocument document = null;
////        try {
////            document = PDDocument.load(inputFile);
////
////            // Set up the permissions
////            AccessPermission permission = new AccessPermission();
////            permission.setCanPrint(true);
////            permission.setCanModify(false);
////            permission.setCanExtractContent(false);  // Disallow content extraction
////
////            // Set up the encryption policy with the password and permissions
////            StandardProtectionPolicy protectionPolicy = new StandardProtectionPolicy(password, password, permission);
////
////            // Set the encryption key length (256 bits for stronger security)
////            protectionPolicy.setEncryptionKeyLength(256);
////
////            // Apply the encryption
////            document.protect(protectionPolicy);
////
////            // Save the protected PDF to the specified output path
////            File outputFile = new File(outputPDFPath);
////            document.save(outputFile);
////
////        } catch (IOException e) {
////            // Handle errors when loading or processing the PDF
////            throw new IOException("Error processing the PDF file: " + e.getMessage(), e);
////        } finally {
////            // Close the document to release resources
////            if (document != null) {
////                document.close();
////            }
////        }
////    }
////}
//
//
//package com.example.pdf.service;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//
//@Service
//public class PDFProtectionService {
//
//    /**
//     * Copies the PDF from the input path to the output path without applying any encryption or protection.
//     *
//     * @param inputPDFPath  The file path of the input PDF to be copied.
//     * @param outputPDFPath The file path where the copied PDF will be saved.
//     * @throws IOException If there is an error loading or saving the PDF.
//     */
//    public void savePDFWithoutEncryption(String inputPDFPath, String outputPDFPath) throws IOException {
//        // Check if the input file exists
//        File inputFile = new File(inputPDFPath);
//        if (!inputFile.exists()) {
//            throw new IOException("Input file does not exist at the specified path: " + inputPDFPath);
//        }
//
//        // Load the PDF document from the input file
//        PDDocument document = null;
//        try {
//            document = PDDocument.load(inputFile);
//
//            // Save the PDF content to the output path without any encryption or protection
//            File outputFile = new File(outputPDFPath);
//            document.save(outputFile); // Saves the PDF content as is
//
//        } catch (IOException e) {
//            // Handle any errors when loading or saving the PDF
//            throw new IOException("Error processing the PDF file: " + e.getMessage(), e);
//        } finally {
//            // Ensure the document is closed to release resources
//            if (document != null) {
//                document.close();
//            }
//        }
//    }
//}



package com.example.pdf.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PDFProtectionService {

    /**
     * Copies the PDF from the input path to the output path without applying any encryption or protection.
     *
     * @param inputPDFPath  The file path of the input PDF to be copied.
     * @param outputPDFPath The file path where the copied PDF will be saved.
     * @throws IOException If there is an error loading or saving the PDF.
     */
    public void savePDFWithoutEncryption(String inputPDFPath, String outputPDFPath) throws IOException {
        // Check if the input file exists
        File inputFile = new File(inputPDFPath);
        if (!inputFile.exists()) {
            throw new IOException("Input file does not exist at the specified path: " + inputPDFPath);
        }

        // Load the PDF document from the input file
        PDDocument document = null;
        try {
            document = PDDocument.load(inputFile);

            // Save the PDF content to the output path without any encryption or protection
            File outputFile = new File(outputPDFPath);
            document.save(outputFile); // Saves the PDF content as is

        } catch (IOException e) {
            // Handle any errors when loading or saving the PDF
            throw new IOException("Error processing the PDF file: " + e.getMessage(), e);
        } finally {
            // Ensure the document is closed to release resources
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * Protects the PDF with a password and applies restrictions.
     *
     * @param inputPDFPath  The file path of the input PDF to be protected.
     * @param outputPDFPath The file path where the protected PDF will be saved.
     * @param password      The password to be applied to the PDF for encryption.
     * @throws IOException If there is an error loading, encrypting, or saving the PDF.
     */
    public void protectPDF(String inputPDFPath, String outputPDFPath, String password) throws IOException {
        // Check if the input file exists
        File inputFile = new File(inputPDFPath);
        if (!inputFile.exists()) {
            throw new IOException("Input file does not exist at the specified path: " + inputPDFPath);
        }

        // Load the PDF document from the input file
        PDDocument document = null;
        try {
            document = PDDocument.load(inputFile);

            // Set up the encryption policy with the password
            StandardProtectionPolicy protectionPolicy = new StandardProtectionPolicy(password, password,AccessPermission.getOwnerAccessPermission());

            // Set the encryption key length (256 bits for stronger security)
            protectionPolicy.setEncryptionKeyLength(256);

            // Set the permissions (disallow modifications, extraction, etc.)
            AccessPermission permission = new AccessPermission();
            permission.setCanPrint(true);
            permission.setCanModify(false);
            permission.setCanExtractContent(false); // Disallow content extraction
            protectionPolicy.setPermissions(permission);

            // Apply the encryption
            document.protect(protectionPolicy);

            // Save the protected PDF to the output path
            File outputFile = new File(outputPDFPath);
            document.save(outputFile);

        } catch (IOException e) {
            // Handle any errors when loading, processing, or saving the PDF
            throw new IOException("Error processing the PDF file: " + e.getMessage(), e);
        } finally {
            // Ensure the document is closed to release resources
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * Opens a password-protected PDF and loads it.
     *
     * @param inputPDFPath The file path of the input PDF.
     * @param password     The password required to open the PDF.
     * @return The loaded PDDocument object if the password is correct.
     * @throws IOException If there is an error opening the PDF.
     */
    public PDDocument openPasswordProtectedPDF(String inputPDFPath, String password) throws IOException {
        // Check if the input file exists
        File inputFile = new File(inputPDFPath);
        if (!inputFile.exists()) {
            throw new IOException("Input file does not exist at the specified path: " + inputPDFPath);
        }

        PDDocument document = null;
        try {
            // Attempt to load the PDF with the password
            document = PDDocument.load(inputFile, password);

            // Return the loaded document if successful
            return document;

        } catch (IOException e) {
            // Handle any errors when loading the password-protected PDF
            throw new IOException("Error opening the password-protected PDF file: " + e.getMessage(), e);
        }
    }
}
