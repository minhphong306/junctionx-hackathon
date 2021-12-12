package response;

import formmaker.junctionx.ModelForm;

import java.util.List;

public class FormListResponse {
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
