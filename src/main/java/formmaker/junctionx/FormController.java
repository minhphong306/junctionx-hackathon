package formmaker.junctionx;

import db.DbField;
import db.DbForm;
import db.DbOption;
import model.ModelField;
import model.ModelForm;
import model.ModelOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.*;

import java.util.*;

@RestController
public class FormController {

    @GetMapping(path = "/admin/forms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormListResponse> getForms() {
        FormListResponse res = new FormListResponse();
        List<ModelForm> forms = DbForm.GetListForm(100, 0);

        res.setSuccess(true);
        res.setData(forms);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/admin/forms/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormSingleResponse> getSingleForm(@PathVariable(required = true) long id) {
        FormSingleResponse res = new FormSingleResponse();
        ModelForm form = DbForm.GetForm(id);
        if (form == null) {
            res.setMessage("Can't get form with id: " + id);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<ModelField> fields = DbField.GetListField(id, 100, 0);
        List<ModelOption> options = DbOption.GetListOption(id, 100, 0);

        // Map option to fields
        Map<Long, List<ModelOption>> optionMap = new HashMap<>();

        for (int i = 0; i < options.size(); i++) {
            ModelOption item = options.get(i);
            List<ModelOption> optionList = optionMap.get(item.getField_id());
            if (optionList == null) {
                optionList = new ArrayList<>();
            }

            optionList.add(item);
            optionMap.put(item.getField_id(), optionList);
        }

        // Build list fields
        List<FormSingleField> resFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            ModelField fieldItem = fields.get(i);
            List<ModelOption> optionList = optionMap.get(fieldItem.getId());

            resFields.add(new FormSingleField(fieldItem.getId(), fieldItem.getUser_id(),
                    fieldItem.getForm_id(), fieldItem.getName(),
                    fieldItem.getType(), fieldItem.getSettings(),
                    fieldItem.isRequired(), optionList));
        }

        FormSingleData data = new FormSingleData();
        data.setId(form.getId());
        data.setName(form.getName());
        data.setDescription(form.getDescription());
        data.setFields(resFields);

        res.setSuccess(true);
        res.setData(data);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/forms/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormDeleteResponse> getForms(@PathVariable(required = true) long id) {
        boolean deleteRes = DbForm.DeleteForm(id);

        FormDeleteResponse res = new FormDeleteResponse();
        if (deleteRes) {
            res.setSuccess(true);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/admin/forms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormCreateResponse> createForm(@RequestBody FormSingleData form) {
        // Create form
        long formId = DbForm.CreateForm(form.getName(), form.getDescription());

        // Create fields
        for (FormSingleField field : form.getFields()) {
            long fieldId = DbField.CreateField(formId, field);

            // TODO: create options
            if (field.getOptions() != null && field.getOptions().size() > 0) {
                for (ModelOption option : field.getOptions()) {
                    DbOption.CreateOption(formId, fieldId, option);
                }
            }
        }

        FormCreateData data = new FormCreateData();
        data.setId(formId);

        FormCreateResponse res = new FormCreateResponse();

        res.setData(data);
        res.setSuccess(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/admin/forms/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormCreateResponse> updateForm(@RequestBody FormSingleData form) {
        // Update form
        long id = form.getId();
        long formId = DbForm.UpdateForm(id, form.getName(), form.getDescription());

        // Delete old field & create again
        DbField.DeleteField(id);

        // TODO: compare & only delete, update if necessary
        for (FormSingleField field : form.getFields()) {
            long fieldId = DbField.CreateField(formId, field);

            if (field.getOptions() != null && field.getOptions().size() > 0) {
                for (ModelOption option : field.getOptions()) {
                    DbOption.CreateOption(formId, fieldId, option);
                }
            }
        }

        FormCreateData data = new FormCreateData();
        data.setId(formId);

        FormCreateResponse res = new FormCreateResponse();

        res.setData(data);
        res.setSuccess(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
