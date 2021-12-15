package com.panambystudios.blocodenotas.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.panambystudios.blocodenotas.R;
import com.panambystudios.blocodenotas.helper.TarefaDAO;
import com.panambystudios.blocodenotas.model.Tarefa;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private EditText editAnotacao;
    private Tarefa tarefaAtual;
    private List<Tarefa> listaAnotacoes = new ArrayList<>();
    private ImageView imageMic;
    public static  final Integer RecordAudioRequestCode=1;

    private SpeechRecognizer speechRecognizer;

    AlertDialog.Builder alertSpeechDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editAnotacao = findViewById(R.id.editTextAnotacao);
        imageMic = findViewById(R.id.imageMic);

        if(ContextCompat.checkSelfPermission(AdicionarTarefaActivity.this, Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent sppechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sppechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        sppechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(AdicionarTarefaActivity.this).inflate(R.layout.alertcustom,
                        viewGroup,false);

                alertSpeechDialog = new AlertDialog.Builder(AdicionarTarefaActivity.this);
                alertSpeechDialog.setMessage("Fale alguma coisa...");
                alertSpeechDialog.setView(dialogView);
                alertDialog = alertSpeechDialog.create();
                alertDialog.show();
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> arrayList=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editAnotacao.setText(arrayList.get(0));
                alertDialog.dismiss();

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        imageMic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    imageMic.setImageResource(R.drawable.ic_baseline_mic_24);
                    speechRecognizer.startListening(sppechIntent);
                }

                return false;
            }
        });


        //Recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            editAnotacao.setText(tarefaAtual.getNomeTarefa());
        }


    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(AdicionarTarefaActivity.this,new String[]{
                    Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemSalvar:
                //Executa ação para o item Salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                if(tarefaAtual != null){
                    //String nomeTarefa = editTarefa.getText().toString();
                    String nomeTarefa = editAnotacao.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        //tarefa.setAnotacao(anotacao);
                        tarefa.setId(tarefaAtual.getId());

                        //Atualizar no banco de dados
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "ERRO ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                }else{//Salvar
                    //String nomeTarefa = editTarefa.getText().toString();
                    String nomeTarefa = editAnotacao.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        //tarefa.setAnotacao(anotacao);
                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "ERRO ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy(){
        super.onDestroy();
        speechRecognizer.destroy();
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==RecordAudioRequestCode && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this,"Permission Grated",Toast.LENGTH_LONG).show();
            }

        }
    }

}
