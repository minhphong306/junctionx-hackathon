package formmaker.junctionx;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
