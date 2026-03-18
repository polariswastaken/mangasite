package dev.polar.reader.controller;

import dev.polar.reader.model.Manga;
import dev.polar.reader.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manga")
public class MangaController {

    // Dependency injection
    @Autowired
    private MangaService mangaService;

    // URL: GET /api/manga
    // Returns: A list of all mangas in JSON format.
    @GetMapping
    public ResponseEntity<List<Manga>> getAllManga() {
        // could add these in return(here) but separated for improved readability
        List<Manga> mangas = mangaService.getAllManga();
        return new ResponseEntity<>(mangas, HttpStatus.OK);
    }

    // URL: GET /api/manga/id?keyword=(Manga ID number)
    // Returns: 1 manga with matching id.
    @GetMapping("/{id}")
    public ResponseEntity<Manga> getMangaById(@PathVariable long id) {

        Manga manga = mangaService.getMangaById(id);
        return new ResponseEntity<>(manga, HttpStatus.OK);
    }

    // URL: GET /api/manga/title/naruto
    @GetMapping("/title/{mangaName}")
    public ResponseEntity<Manga> getMangaByName(@PathVariable String mangaName) {

        Manga foundManga = mangaService.getMangaByName(mangaName);
        return new ResponseEntity<>(foundManga, HttpStatus.OK);
    }


    // URL: GET /api/manga/search?keyword=Solo
    // Returns: Mangas matching the keyword.
    // @RequestParam extracts "Solo" from "?keyword=Solo"
    @GetMapping("/search")
    public ResponseEntity<List<Manga>> searchManga(@RequestParam String keyword) {

        List<Manga> results = mangaService.searchManga(keyword);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    // URL: POST /api/manga
    // Body: { "title": "Solo Leveling", "type": "MANHWA" ... }
    // @RequestBody: Takes the JSON sent by the user and turns it into a Java 'Manga' object.
    @PostMapping
    public ResponseEntity<Manga> addManga(@RequestBody Manga manga) {

        Manga newManga = mangaService.addManga(manga);
        return new ResponseEntity<>(newManga, HttpStatus.CREATED);
    }

    // URL: DELETE /api/manga/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id) {
        // todo ADD DELETION LOGIC
        return new ResponseEntity<>(HttpStatus.OK);
    }
}