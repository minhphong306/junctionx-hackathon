package response;

public class FormSingleResponse {
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
