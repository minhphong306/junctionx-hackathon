package formmaker.junctionx;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FormController {

    class FormResponse {
        public boolean success;
        public String message;
        public List<ModelForm> data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ModelForm> getData() {
            return data;
        }

        public void setData(List<ModelForm> data) {
            this.data = data;
        }
    }

    class FormSingleField {
        private long id;
        private long user_id;
        private long form_id;
        private String name;
        private String type;
        private String settings;
        private boolean required;
        private List<ModelOption> options;

        public FormSingleField(long id, long user_id, long form_id, String name, String type, String settings, boolean required, List<ModelOption> options) {
            this.id = id;
            this.user_id = user_id;
            this.form_id = form_id;
            this.name = name;
            this.type = type;
            this.settings = settings;
            this.required = required;
            this.options = options;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public long getForm_id() {
            return form_id;
        }

        public void setForm_id(long form_id) {
            this.form_id = form_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSettings() {
            return settings;
        }

        public void setSettings(String settings) {
            this.settings = settings;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public List<ModelOption> getOptions() {
            return options;
        }

        public void setOptions(List<ModelOption> options) {
            this.options = options;
        }
    }

    class FormSingleData {
        public long id;
        public long user_id;
        public String name;
        public String description;
        public List<FormSingleField> fields;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<FormSingleField> getFields() {
            return fields;
        }

        public void setFields(List<FormSingleField> fields) {
            this.fields = fields;
        }
    }

    class FormSingleResponse {
        public boolean success;
        public String message;
        public FormSingleData data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public FormSingleData getData() {
            return data;
        }

        public void setData(FormSingleData data) {
            this.data = data;
        }
    }

    class FormDeleteResponse {
        public boolean success;
        public String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


    @GetMapping(path = "/admin/forms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormResponse> getForms() {
        FormResponse res = new FormResponse();
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
            res.setMessage("Can't get form");
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

}
