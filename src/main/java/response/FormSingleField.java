package response;

import model.ModelOption;

import java.util.List;

public class FormSingleField {
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
