package response;

public class FormCreateResponse {

    public boolean success;
    public String message;
    public FormCreateData data;

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

    public FormCreateData getData() {
        return data;
    }

    public void setData(FormCreateData data) {
        this.data = data;
    }
}

