package com.example.ocr.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Controller
public class OCRController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/extract")
    public String extractText(@RequestParam("image") MultipartFile image, Model model) {
        String result = "";
        try {
            File tempFile = File.createTempFile("upload-", image.getOriginalFilename());
            image.transferTo(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // Update path
            result = tesseract.doOCR(tempFile);

        } catch (TesseractException e) {
            result = "OCR Error: " + e.getMessage();
        } catch (Exception ex) {
            result = "Upload Error: " + ex.getMessage();
        }

        model.addAttribute("text", result);
        return "index";
    }
}
