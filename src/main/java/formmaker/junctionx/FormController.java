package formmaker.junctionx;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping(path = "/admin/forms", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormResponse> getForms() {
        FormResponse res = new FormResponse();
        List<ModelForm> forms = DbForm.GetListForm(100, 0);

        res.setSuccess(true);
        res.setData(forms);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
