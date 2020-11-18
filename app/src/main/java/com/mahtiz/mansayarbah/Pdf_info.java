package com.mahtiz.mansayarbah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Pdf_info extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_info);
        pdfView=findViewById(R.id.pdfView);
        pdfView.fromAsset("infoApp.pdf").load();
    }
}