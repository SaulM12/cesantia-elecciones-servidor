package com.cesantia.elections.controllers;

import com.cesantia.elections.services.ActaEleccionService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ActaEleccionController {
    private final ActaEleccionService actaEleccionService;

    public ActaEleccionController(ActaEleccionService actaEleccionService) {
        this.actaEleccionService = actaEleccionService;
    }

    @GetMapping("/acta-eleccion/pdf")
    public ResponseEntity<Resource> generatePdf() throws Exception {
        // Generar HTML dinámico con Thymeleaf
        String html = actaEleccionService.generateHtml();

        // Convertir HTML a PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.toStream(outputStream);
        builder.withHtmlContent(html, "file:///");
        builder.run();

        // Convertir el PDF a un recurso descargable
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=acta_eleccion.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength()) // Evita problemas en algunos navegadores
                .body(resource); // Correcto: ByteArrayResource es un Resource
    }
}
