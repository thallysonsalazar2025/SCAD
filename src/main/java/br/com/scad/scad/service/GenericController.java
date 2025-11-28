//package br.com.scad.scad.service;
//
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//public interface GenericController {
//    default URI getBaseUri(Long uuid) {
//        return ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(uuid)
//                .toUri();
//    }
//}
