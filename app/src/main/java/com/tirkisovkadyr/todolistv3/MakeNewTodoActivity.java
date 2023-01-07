package com.tirkisovkadyr.todolistv3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class MakeNewTodoActivity extends AppCompatActivity {
    private final String FILE_NAME = "content.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_todo);

        Button btnDelete = findViewById(R.id.delete_btn);
        Button btnAdd = findViewById(R.id.add_btn);

        btnDelete.setOnClickListener(this::onClickDelete);
        btnAdd.setOnClickListener(this::onClickAdd);

        EditText edtTheme = findViewById(R.id.editTextTheme);
        EditText edtMultiLine = findViewById(R.id.editTextTextMultiLine);



    }

    public void onClickDelete(View view) {
        finish();
    }

    public void onClickAdd(View view) {
        FileOutputStream fos = null;
        FileInputStream fin = null;
        String textJson = "";
        HashMap<String, TODO> allTodos = null;
        TODO newTodo = null;
        String newKey = "0";

//        ArrayList<Integer> arr = new ArrayList<>();
//
//        Collections.sort(arr, (d1, d2) -> {
//            return d1 - d2;
//        });

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            textJson = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(textJson);
//            textJson = String.valueOf(bytes);
//            System.out.println(textJson);

            /*
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            System.out.println(text);
            todoHashMap = objectMapper.readValue(text, new TypeReference<HashMap<String, TODO>>(){});
            System.out.println(todoHashMap);
             */
        } catch (IOException ex) {
            System.out.println("ADD (2) SECOND EXCEPTION IOE");
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }


        try {
            EditText theme = findViewById(R.id.editTextTheme);
            EditText extraDesc = findViewById(R.id.editTextExtraDesc);

            String themeStr = theme.getText().toString();
            String description = extraDesc.getText().toString();

            newTodo = new TODO(themeStr, description);
            try {
                if (!"".equals(textJson)) {
                    System.out.println(textJson);
                    allTodos = objectMapper.readValue(textJson, new TypeReference<HashMap<String, TODO>>(){});
                    System.out.println("text json is empty!");
                } else {
                    allTodos = new HashMap<String, TODO>();
                    System.out.println("Text JSON NOT empty!");
                }
            } catch (JsonMappingException ex) {
                System.out.println("ADD (3) THIRD EXCEPTION");
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            } catch (IOException ex1) {
                System.out.println("ADD (3) THIRD EXCEPTION IOE");
                Toast.makeText(this, ex1.getMessage(), Toast.LENGTH_LONG).show();
                ex1.printStackTrace();
            }


            assert allTodos != null;
//            if (allTodos.keySet().size() != 0) {
//            }
            newKey = findNewApplicableKeyForHashMap(allTodos.keySet());

            allTodos.put(newKey, newTodo);

            try {
                System.out.println(textJson);
                textJson = objectWriter.writeValueAsString(allTodos);
            } catch (JsonMappingException ex1) {
                System.out.println("ADD (4) FOURTH EXCEPTION");
                Toast.makeText(this, ex1.getMessage(), Toast.LENGTH_LONG).show();
                ex1.printStackTrace();
            } catch (IOException ex1) {
                System.out.println("ADD (4) FOURTH EXCEPTION IOE");
                Toast.makeText(this, ex1.getMessage(), Toast.LENGTH_LONG).show();
                ex1.printStackTrace();
            }




//            String text = theme.getText().toString();
//            text += "\n\n" + extraDesc.getText().toString();
//
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(textJson.getBytes(StandardCharsets.UTF_8));
//            fos.write(text.getBytes(StandardCharsets.UTF_8));


//            String json = objectMapper.writeValueAsString(todo);

//            Object js = objectMapper.readValue(json, Object.class);
//            String jsonIndented = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(js);
//
//            fos = openFileOutput(FILE_NAME, MODE_APPEND);
//            fos.write(jsonIndented.getBytes(StandardCharsets.UTF_8));

            Toast.makeText(this, "TODO has been saved!", Toast.LENGTH_SHORT).show();

        }
        catch (IOException ex) {
            System.out.println("ADD (5) FIFTH EXCEPTION IOE");
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ex) {
                System.out.println("ADD (6) SIXTH EXCEPTION IOE");
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
            finish();
        }
    }

    protected String findNewApplicableKeyForHashMap(Set<String> keys) {
        int key = 0;

        ArrayList<String> mKeys = new ArrayList<>(keys);

        Collections.sort(mKeys, (first, second) -> {
            return Integer.parseInt(first) - Integer.parseInt(second);
        });

        for (String k : mKeys) {
            if (Integer.parseInt(k) == key) {
                key++;
            } else {
                return String.valueOf(key);
            }
        }
        return String.valueOf(key);
    }
}