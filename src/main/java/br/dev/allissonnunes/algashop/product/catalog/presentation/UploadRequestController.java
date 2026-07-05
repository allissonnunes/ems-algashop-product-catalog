package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.upload.UploadRequestApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.upload.UploadRequestInput;
import br.dev.allissonnunes.algashop.product.catalog.application.upload.UploadResponseOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/upload-requests")
@RequiredArgsConstructor
class UploadRequestController {

    private final UploadRequestApplicationService uploadRequestApplicationService;

    @PostMapping
    public ResponseEntity<UploadResponseOutput> requestUpload(@RequestBody final @Valid UploadRequestInput input) {
        return ResponseEntity.ok(uploadRequestApplicationService.requestPreSignedUrl(input));
    }

}
