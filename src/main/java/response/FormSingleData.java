package response;

import java.util.List;

public class FormSingleData {
    public long id;
    public long user_id;
    public int status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
