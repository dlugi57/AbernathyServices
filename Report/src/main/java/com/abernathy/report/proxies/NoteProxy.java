package com.abernathy.report.proxies;

import com.abernathy.report.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "note", url = "${NOTE_BASE_URL:http://localhost:8086}")
public interface NoteProxy {

    /**
     * Add note
     *
     * @param note note object
     * @return status and uri with new created patient
     */
    @PostMapping(value = "/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    boolean addNote(@Valid @RequestBody Note note);

    /**
     * Update note
     *
     * @param note note object
     * @return status and uri with new created note
     */
    @PutMapping(value = "/patHistory/update")
    @ResponseStatus(HttpStatus.CREATED)
    boolean updateNote(@Valid @RequestBody Note note);

    /**
     * Delete note
     *
     * @param id id of note
     */
    @DeleteMapping(value = "/patHistory/delete")
    @ResponseStatus(HttpStatus.OK)
    boolean deleteNote(@RequestParam String id);

    /**
     * Get note
     *
     * @param id note id
     * @return note object
     */
    @GetMapping(value = "/patHistory/get")
    @ResponseStatus(HttpStatus.OK)
    Note getNote(@RequestParam() String id);

    /**
     * Get note by patient id
     *
     * @param id patient id
     * @return note object
     */
    @GetMapping(value = "/patHistory/getByPatientId")
    @ResponseStatus(HttpStatus.OK)
    List<Note> getNotesByPatientId(@RequestParam() Integer id);
}
