package radconcursosfree.mxtechnologies.com.radiologiaconcursosfree;

import android.app.Fragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

/**
 * Created by Moisés on 08/02/2016.
 */
public class rad20 extends Fragment {
    private TextView questaoTextView;
    private TextView textView;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;
    private RadioButton e;
    private RadioGroup rg;
    private int opcao;
    private int tentativas;
    private int alternativa;
    int[] nquestion;
    int nquestions;
    int m;
    InterstitialAd mInterstitialAd;
    MediaPlayer mp,error,fim;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.content_radiologia, container, false);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView mAdView2 = (AdView) view.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        mp = MediaPlayer.create(getActivity(), R.raw.sorrisobb);
        mAdView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.start();
            }
        });

        //UnityAds.init(getActivity(), "1074695", new UnityAdsListener());

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-6303877676651382/5552620957");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        nquestions = 100;
        m=0;
        tentativas=0;
        textView = (TextView) view.findViewById(R.id.textView);
        questaoTextView = (TextView) view.findViewById(R.id.questaoTextView);
        rg = (RadioGroup)view.findViewById(R.id.rgopcoes);
        a = (RadioButton)view.findViewById(R.id.a);
        b = (RadioButton)view.findViewById(R.id.b);
        c = (RadioButton)view.findViewById(R.id.c);
        d = (RadioButton)view.findViewById(R.id.d);
        e = (RadioButton)view.findViewById(R.id.e);
        nquestion = new int[20];
        int tquestions [];
        int aux;
        Random random  = new Random();
        tquestions = new int[nquestions];
        for(int b=0;b<nquestions;b++){
            tquestions[b]=b+1;
        }
        for (int n=0;n<20;n++){
            do{
                aux=random.nextInt(nquestions);
                nquestion[n]=tquestions[aux];
            }while(tquestions[aux]==0);
            tquestions[aux]=0;
        }


        alternativa=update(nquestion[m]);
        textView.setText((m + 1) + " de "+nquestion.length);

        ImageView button1 = (ImageView)view.findViewById(R.id.button1);
        button1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        opcao = rg.getCheckedRadioButtonId();
                        alternativa = update(nquestion[m]);
                        textView.setText((m + 1) + " de "+nquestion.length);
                        if (opcao != alternativa) {
                            tentativas = tentativas + 1;
                            error = MediaPlayer.create(getActivity(), R.raw.error);
                            error.start();
                            AlertDialog.Builder mensagem = new AlertDialog.Builder(getActivity());
                            mensagem.setTitle("Atenção!");
                            mensagem.setMessage("Alternativa incorreta");
                            mensagem.setNeutralButton("OK", null);
                            mensagem.show();
                        }

                        if (opcao == alternativa) {
                            if (m == (nquestion.length-1)) {
                                tentativas = tentativas + 1;
                                fim = MediaPlayer.create(getActivity(), R.raw.chinesegong);
                                fim.start();
                                AlertDialog.Builder mensagem1 = new AlertDialog.Builder(getActivity());
                                mensagem1.setTitle("   ESTATÍSTICAS");
                                mensagem1.setMessage(tentativas + " tentativas para responder " + nquestion.length + " questões");
                                mensagem1.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (mInterstitialAd.isLoaded()) {
                                            mInterstitialAd.show();
                                        }
                                        //if ( UnityAds.canShow() )
                                        //{
                                        //  UnityAds.show();
                                        //}
                                    }
                                });
                                mensagem1.show();
                            }

                            if (m < (nquestion.length-1)) {
                                tentativas = tentativas + 1;
                                AlertDialog.Builder mensagem = new AlertDialog.Builder(getActivity());
                                mensagem.setTitle("Parabéns!");
                                mensagem.setMessage("Alternativa correta");
                                mensagem.setNeutralButton("OK", null);
                                mensagem.show();
                                m = m + 1;
                                alternativa = update(nquestion[m]);
                                textView.setText((m + 1) + " de " + nquestion.length);
                            }
                        }
                    }
                });
        ImageView button2 = (ImageView)view.findViewById(R.id.button2);
        button2.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick (View v){
                        if(m>0) {
                            m = m - 1;
                            alternativa = update(nquestion[m]);
                            textView.setText((m + 1) + " de "+nquestion.length);
                        }else{
                            Toast.makeText(getActivity(), "Início do Teste", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


        return view;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private int update(int question) {
        int alt = -1;
        if (question == 0) {
            questaoTextView.setText("Fratura da metade proximal da ulna, seguida de deslocamento da cabeça do rádio. Esta é a definição da fratura de: ");
            a.setText("Colles");
            b.setText("Smith");
            c.setText("Monteggia");
            d.setText("Bennet");
            e.setText("Baleazzi");
            alt = R.id.b;
        }
        if (question == 1) {
            questaoTextView.setText("O hormônio Cortisol é produzido no(a): ");
            a.setText("pâncreas");
            b.setText("hipófise");
            c.setText("paratireóide");
            d.setText("supra renal");
            e.setText("adeno hipófise");
            alt = R.id.d;
        }
        if (question == 2) {
            questaoTextView.setText("O fator secundário da densidade é o(a): ");
            a.setText("tempo");
            b.setText("miliamperagem");
            c.setText("distorção");
            d.setText("absorção");
            e.setText("kilovoltagem");
            alt = R.id.b;
        }
        if (question == 3) {
            questaoTextView.setText("Atualmente, a qualidade de imagem é considerada fator primordial na aquisição e melhoramento radiográfico.  Considerando os fatores de controle e correção deste padrão, como se deve corrigir uma radiografia subexposta de mão, realizada com fator técnico de 30 kvp, duplicando-se a densidade?  ");
            a.setText("25,5 kVp");
            b.setText("60 kVp");
            c.setText("34,5 kVp");
            d.setText("30 kVp");
            e.setText("15 kVp");
            alt = R.id.a;
        }
        if (question == 4) {
            questaoTextView.setText("A estrutura anatômica ‘’junção ureterovesical’’ está localizada no(a):  ");
            a.setText("rim");
            b.setText("uretra");
            c.setText("bexiga");
            d.setText("vesícula biliar");
            e.setText("ureter");
            alt = R.id.d;
        }
        if (question == 5) {
            questaoTextView.setText("Segundo a portaria 453/1998, qual o valor mínimo da camada semi-redutora, quando a tensão máxima de um tubo de operação é de 120 kvp, com rendimento de um gerador trifásico? ");
            a.setText("2,5");
            b.setText("3,0");
            c.setText("3,2");
            d.setText("3,5");
            e.setText("3,9");
            alt = R.id.e;
        }
        if (question == 6) {
            questaoTextView.setText("Em relação aos meios de contrastes iônicos, quais são consideradas reações moderadas?");
            a.setText("Tosse, tremores, hipotensão e edema de glote");
            b.setText("Rubor, sudorese, hipotensão e arritmias");
            c.setText("Hipotensão, hipertensão, edema facial e cefaléia intensa");
            d.setText("Urticária intensa, edema de glote, dor torácica e tremores");
            e.setText("Laringo espasmo, tosse, colapso vascular severo e edema agudo de pulmão");
            alt = R.id.d;
        }
        if (question == 7) {
            questaoTextView.setText("Em tomografia computadorizada, de acordo com a escala de Hounsfield, um tecido do tipo ósseo poroso equivale a:  ");
            a.setText("-900 a -450H");
            b.setText("70 a 80H");
            c.setText("100 a 200H");
            d.setText("600 a 700H");
            e.setText("acima de 1.000H");
            alt = R.id.a;
        }
        if (question == 8) {
            questaoTextView.setText("Secção tomográfica axial, adquirida ao nível do teto acetabular, demonstra: ");
            a.setText("glúteo médio, asa ilíaca e vértebra L-5");
            b.setText("glúteo maior, corpo do ilíaco e bexiga");
            c.setText("glúteo menor, cólon ascendente e reto");
            d.setText("glúteo maior, asa ilíaca e bexiga");
            e.setText("glúteo médio, corpo do ilíaco e vértebra  L-5");
            alt = R.id.b;
        }
        if (question == 9) {
            questaoTextView.setText("É considerada contra-indicação absoluta para a realização de angiografia digital periférica: ");
            a.setText("doença aterosclerótica");
            b.setText("oclusão ou estreitamento de vaso");
            c.setText("traumatismo");
            d.setText("aneurisma cerebral");
            e.setText("embolia ou trombose");
            alt = R.id.d;
        }
        if (question == 10) {
            questaoTextView.setText("Em um determinado exame de raios X, o técnico ajustou os fatores técnicos com 420 mA e tempo de exposição de 1/60 segundo.  Nesse contexto, é correto afirmar que ele utilizou um fator de exposição de: ");
            a.setText("0,7 mAs");
            b.setText("7 mAs");
            c.setText("70 mAs");
            d.setText("0,07 mAs");
            e.setText("700 mAs");
            alt = R.id.b;
        }
        if (question == 11) {
            questaoTextView.setText("Qual o método frequentemente utilizado para cateterismo e subsequente realização de angiografia? ");
            a.setText("Técnica de Lewis");
            b.setText("Técnica de Roberts");
            c.setText("Técnica de Seldinger");
            d.setText("Técnica de Clark");
            e.setText("Técnica de Moret");
            alt = R.id.d;
        }
        if (question == 12) {
            questaoTextView.setText("O fêmur é uma estrutura densa e os raios X, para cruzarem esta estrutura, necessitam de uma onda altamente penetrante. Diante disso, é necessária uma ampola de ânodo giratório. Assinale corretamente a alternativa que contém os respectivos componentes que formam a ampola e os constituintes da camada que revestem o fêmur, interna e externamente. ");
            a.setText("Ferro, ar, chumbo, vidro e óleo; endósteo e pericôndrio");
            b.setText("Ferro, chumbo, vácuo, vidro e óleo; epimisio e periósteo");
            c.setText("Ferro, vácuo, óleo, vidro e tungstênio; endósteo e periósteo");
            d.setText("Ferro, chumbo, vidro, vácuo e tungstênio; periósteo e endósteo");
            e.setText("Ferro, chumbo, óleo, vidro e vácuo; endósteo e periósteo");
            alt = R.id.e;
        }
        if (question == 13) {
            questaoTextView.setText("Na Doença de Scheuermann, qual segmento da coluna vertebral é largamente afetado? ");
            a.setText("Sacro");
            b.setText("Cervical");
            c.setText("Cóccix");
            d.setText("Torácica e Lombar");
            e.setText("Disco vertebral");
            alt = R.id.b;
        }
        if (question == 14) {
            questaoTextView.setText("A Fratura de Pott está diretamente ligada a que tipo de estrutura óssea? ");
            a.setText("1/3 distal do rádio");
            b.setText("1/3 médio do fêmur");
            c.setText("1/3 distal da fíbula");
            d.setText("1/3 proximal do antebraço");
            e.setText("1/3 distal do úmero");
            alt = R.id.c;
        }
        if (question == 15) {
            questaoTextView.setText("O estresse em eversão do tornozelo também pode ser classificado como: ");
            a.setText("dorsiflexão");
            b.setText("rotação");
            c.setText("valgo");
            d.setText("abdução");
            e.setText("circundação");
            alt = R.id.a;
        }
        if (question == 16) {
            questaoTextView.setText("A única sindesmose verdadeira do corpo humano está localizada na: ");
            a.setText("articulação temporo-mandibular");
            b.setText("sutura craniana");
            c.setText("articulação esterno-clavicular");
            d.setText("articulação atlanto-axial");
            e.setText("articulação tibiofibular distal");
            alt = R.id.e;
        }
        if (question == 17) {
            questaoTextView.setText("Existem vários planos e linhas imaginários que podem ser traçados nas cavidades abdominal e pélvica com o objetivo de facilitar a localização de estruturas anatômicas, no estudo radiológico convencional do abdome. O plano transumbilical é um plano transversal que passa através da cicatriz umbilical e de qual espaço intervertebral?");
            a.setText("L1-L2");
            b.setText("L2-L3");
            c.setText("L3-L4");
            d.setText("L4-L5");
            e.setText("L5-S1");
            alt = R.id.c;
        }
        if (question == 18) {
            questaoTextView.setText("Na rotina do estudo radiográfico convencional da sela turca, a incidência localizada semiaxial anteroposterior, também denominada Reverchon, é utilizada para:");
            a.setText("avaliação da estrutura óssea do dorso da sela");
            b.setText("visualização do processo clinoide anterior");
            c.setText("mensuração do quiasma óptico");
            d.setText("confirmação de lesões da haste hipofisária");
            e.setText("delineação da linha de Chamberlain");
            alt = R.id.a;
        }
        if (question == 19) {
            questaoTextView.setText("Quais são as incidências utilizadas na rotina radiográfica básica para avaliação da articulação talocrural?");
            a.setText("Anteroposterior e tangencial");
            b.setText("Oblíquas externa e interna");
            c.setText("Tangencial e perfil interno");
            d.setText("Perfil externo e oblíqua interna");
            e.setText("Anteroposterior e perfil externo");
            alt = R.id.e;
        }
        if (question == 20) {
            questaoTextView.setText("No estudo radiográfico do cotovelo, existem pontos anatômicos de referência superficial que são úteis para facilitar a realização do exame. Dentre eles, o olécrano, que é palpável em que região do cotovelo?");
            a.setText("Medial");
            b.setText("Lateral");
            c.setText("Posterior");
            d.setText("Anterior");
            e.setText("Radial");
            alt = R.id.c;
        }
        if (question == 21) {
            questaoTextView.setText("O exame contrastado que tem como objetivo a opacificação das vias urinárias superiores através de uma punção direta do rim, no qual normalmente são realizadas incidências panorâmicas do abdome em decúbito ventral em posteroanterior, é denominado de:");
            a.setText("urografia excretora");
            b.setText("uretrocistografia");
            c.setText("pielografia retrógrada");
            d.setText("pielografia anterógrada");
            e.setText("sialografia");
            alt = R.id.d;
        }
        if (question == 22) {
            questaoTextView.setText("Como é denominado o equipamento radiológico móvel, simples, usado para a realização de exames radiográficos no leito, inclusive em Unidades de Terapia Intensiva e Coronárias?");
            a.setText("Fixo");
            b.setText("Transportável");
            c.setText("Arco cirúrgico");
            d.setText("Radioscopia");
            e.setText("Telecomandado");
            alt = R.id.b;
        }
        if (question == 23) {
            questaoTextView.setText("Como é denominada a incidência complementar no estudo radiográfico da articulação do quadril, na qual o paciente deve estar deitado em decúbito dorsal na mesa, com os membros inferiores estendidos e posicionados com a região posterior apoiada na mesa, devendo-se rodar o paciente para o lado oposto a ser examinado, de maneira que a região dorsal forme um ângulo de aproximadamente 45º com a superfície da mesa e o lado a ser examinado fique mais afastado da superfície da mesa, ficando o membro inferior do lado a ser examinado estendido, e o contralateral, flexionado?");
            a.setText("Farril");
            b.setText("Fergunson");
            c.setText("Obturatriz");
            d.setText("Chaussé");
            e.setText("Nahum");
            alt = R.id.c;
        }
        if (question == 24) {
            questaoTextView.setText("Os seios da face são cavidades aeríficas localizadas em alguns ossos da cabeça. Qual deles está presente antes do nascimento, atingindo o tamanho total na adolescência, possuindo paredes finas, sendo o teto formado pelo assoalho das órbitas?");
            a.setText("Frontais");
            b.setText("Maxilares");
            c.setText("Etmoidais");
            d.setText("Esfenoidais");
            e.setText("Zigomáticos");
            alt = R.id.e;
        }
        if (question == 25) {
            questaoTextView.setText("No estudo radiográfico da coluna cervical, a incidência complementar que demonstra com razoável definição as vértebras cervicais inferiores e as torácicas superiores é:");
            a.setText("anteroposterior");
            b.setText("oblíqua anteroposterior");
            c.setText("perfil esquerdo dinâmico");
            d.setText("perfil com o braço levantado");
            e.setText("oblíqua em transoral");
            alt = R.id.d;
        }
        if (question == 26) {
            questaoTextView.setText("Em relação aos equipamentos de proteção individual, as vestimentas plumbíferas  não devem ser dobradas e quando não estiverem em uso, devem ser mantidas de forma a preservar sua integridade. Como isto é conseguido?");
            a.setText("Mantendo-a sem local seco e livre de umidade");
            b.setText("Colocando-as sobre superfície horizontal ou em suporte apropriado");
            c.setText("Fazendo irradiação com baixa dosagem de Raios-X pelo menos uma vez ao mês");
            d.setText("Descartando-as após um ano de uso");
            e.setText("Enviando-as para esterilização periodicamente");
            alt = R.id.b;
        }
        if (question == 27) {
            questaoTextView.setText("Qual dos princípios básicos de proteção radiológica estabelece que as instalações e as práticas devem ser planejadas, implantadas e executadas de modo que a magnitude das doses individuais, o número de pessoas expostas e a probabilidade de exposições acidentais sejam tão baixos quanto razoavelmente exequíveis?");
            a.setText("Justificação");
            b.setText("Limitação");
            c.setText("Prevenção");
            d.setText("Otimização");
            e.setText("Responsabilização");
            alt = R.id.d;
        }
        if (question == 28) {
            questaoTextView.setText("O osso cuboide, que pode ser visualizado nas incidências em oblíquas e anteroposterior, está localizado em qual região anatômica?");
            a.setText("Mão");
            b.setText("Punho");
            c.setText("Pé");
            d.setText("Quadril");
            e.setText("Cotovelo");
            alt = R.id.c;
        }
        if (question == 29) {
            questaoTextView.setText("Qual é a incidência complementar em mamografia, na qual a paciente deve estar preferencialmente em posição ortostática, com a cabeça virada para o lado que está em estudo, o membro superior contralateral deve estar estendido e com a paciente de frente para o aparelho, o operador deve girar o conjunto tubo de Raios-X/chassi em cerca de 45º e colocar a região da axila e a parte superior do braço sobre o bucky?");
            a.setText("Craniocaudal");
            b.setText("Mediolateral oblíqua");
            c.setText("Perfil lateromedial");
            d.setText("Axilar");
            e.setText("Craniocaudal exagerada");
            alt = R.id.d;
        }
        if (question == 30) {
            questaoTextView.setText("As incidências oblíquas posteriores internas semiaxiais correspondem a uma série de quatro incidências que variam em função do ângulo de entrada do raio central. Utilizadas como complementares no estudo radiográfico do tornozelo, são também denominadas de incidência de:");
            a.setText("Broden");
            b.setText("Cleavage");
            c.setText("Stenvers");
            d.setText("Judet");
            e.setText("Fergunson");
            alt = R.id.a;
        }
        if (question == 31) {
            questaoTextView.setText("Como é denominada a incidência complementar no estudo radiográfico do ápice pulmonar, do lobo médio e dos segmentos lingulares, na qual o paciente deve estar em ortostática, com a região posterior do tórax próxima à superfície do bucky vertical, sendo que as clavículas aparecem projetadas fora dos campos pulmonares?");
            a.setText("Anteroposterior exagerada");
            b.setText("Oblíqua anterior esquerda");
            c.setText("Oblíqua posteroanterior direita");
            d.setText("Decúbito lateral (Hjelm-Laurell)");
            e.setText("Apicolordótica em anteroposterior");
            alt = R.id.e;
        }
        if (question == 32) {
            questaoTextView.setText("O esqueleto humano adulto é formado por 206 ossos distintos que compõem a estrutura de todo o organismo. É dividido em esqueleto axial e apendicular. Quais dos ossos apresentados a seguir fazem parte do esqueleto apendicular? ");
            a.setText("Clavículas, Cervical e Úmero");
            b.setText("Escápulas, Ulna e Rádio");
            c.setText("Fêmur, Fíbula e Costelas");
            d.setText("Esterno, Tíbia e Fêmur");
            e.setText("Rádio, Falanges e Ossos da Face");
            alt = R.id.b;
        }
        if (question == 33) {
            questaoTextView.setText("A construção da câmara escura deve obedecer aos requisitos especificados na Portaria 453 de 1 de junho de 1998. Dentre esses requisitos está a distância da iluminação de segurança. De acordo com a portaria, a distância da lâmpada em relação ao local de manipulação dos filmes não deve ser inferior a");
            a.setText("1,5 metros");
            b.setText("2,5 metros");
            c.setText("1,2 metros");
            d.setText("5 metros");
            e.setText("1,3 metros");
            alt = R.id.c;
        }
        if (question == 34) {
            questaoTextView.setText("As imagens radiográficas realizadas em filmes são avaliadas com base em quatro fatores de qualidade. Quais são esses?");
            a.setText("Densidade, contraste, resolução e distorção");
            b.setText("Detalhe, contraste, ruído e nitidez");
            c.setText("Brilho, distorção, densidade e nitidez");
            d.setText("Distorção, índice de exposição, contraste e densidade");
            e.setText("Ruído, brilho, detalhe e contraste");
            alt = R.id.a;
        }
        if (question == 35) {
            questaoTextView.setText("As imagens radiográficas digitais são visualizadas em um monitor de computador. Cada imagem digital é formada por uma matriz de quadros de elementos chamada de ");
            a.setText("Voxel");
            b.setText("Bit");
            c.setText("Pitch");
            d.setText("Pixel");
            e.setText("Fov");
            alt = R.id.d;
        }
        if (question == 36) {
            questaoTextView.setText("Uma das vantagens da radiografia digital é a transferência rápida das imagens por meio eletrônico dentro de um hospital, centro cirúrgico ou consultório. É possível também enviar as imagens para outros hospitais ou centros especializados para consultas por especialistas ou para laudo. A transmissão eletrônica de imagens de uma localidade para outra, com o propósito de interpretação ou consulta, é conhecida como ");
            a.setText("PACS");
            b.setText("Telerradiologia");
            c.setText("DICOM");
            d.setText("Imaginologia");
            e.setText("Transferência");
            alt = R.id.b;
        }
        if (question == 37) {
            questaoTextView.setText("Os fatores utilizados para avaliar a qualidade de uma imagem digital são seis. A definição “Distúrbio aleatório que obscurece ou reduz a nitidez. Em uma imagem radiográfica, isso se traduz em aparência granulada ou pontilhada da imagem” refere-se ao fator ");
            a.setText("ruído");
            b.setText("contraste");
            c.setText("resolução");
            d.setText("distorção");
            e.setText("brilho");
            alt = R.id.a;
        }
        if (question == 38) {
            questaoTextView.setText("Na Radiologia digital, o chassi com filme foi substituído por cassete digital ou RD (Radiografia Digital Direta). O tamanho do chassi é determinado em centímetros. No cassete digital, o tamanho é determinado em polegadas. Um cassete digital de tamanho 10X12 polegadas equivale em tamanho a um chassi de ");
            a.setText("18x24 cm");
            b.setText("24x24 cm");
            c.setText("24x30 cm");
            d.setText("35X35 cm");
            e.setText("35x43 cm");
            alt = R.id.c;
        }
        if (question == 38) {
            questaoTextView.setText("O princípio de proteção denominado ALARA é muito eficiente na proteção de pacientes e principalmente do técnico. Esse é um princípio de segurança com o objetivo de minimizar as doses de radiação em pacientes e trabalhadores. O que está de acordo com o princípio ALARA?");
            a.setText("Não usar dispositivo de restrição e sempre segurar o paciente quando necessário na hora do exame");
            b.setText("Realizar o exame com o colimador totalmente aberto");
            c.setText("Segurar o paciente sem o uso de avental plumbífero ou protetor de tireoide");
            d.setText("Possibilitar que acompanhantes permaneçam sem necessidade na sala na hora do exame");
            e.setText("Sempre usar um dosímetro de monitoramento pessoal");
            alt = R.id.e;
        }
        if (question == 39) {
            questaoTextView.setText("“São efeitos em que a probabilidade de ocorrência é proporcional à dose de radiação recebida, sem a existência de limiar. Isso significa que doses pequenas, abaixo dos limites estabelecidos por normas e recomendações de radioproteção, podem induzir a tais efeitos”. A descrição citada refere-se a qual efeito biológico da radiação? ");
            a.setText("Efeitos estocásticos");
            b.setText("Efeitos determinísticos");
            c.setText("Efeitos somáticos");
            d.setText("Efeitos genéticos ou hereditários");
            e.setText("Efeitos imediatos e tardios");
            alt = R.id.a;
        }
        if (question == 40) {
            questaoTextView.setText("De acordo com as diretrizes de proteção radiológica, a idade mínima exigida para que uma pessoa possa atuar como profissional de radiologia é de ");
            a.setText("17 anos");
            b.setText("20 anos");
            c.setText("25 anos");
            d.setText("18 anos");
            e.setText("22 anos");
            alt = R.id.d;
        }
        if (question == 41) {
            questaoTextView.setText("Exames de Tomografia Computadorizada e Ressonância Magnética utilizam planos imaginários (cortes) que passam através do corpo em posição anatômica. Os quatros planos comuns utilizados nesse tipo de exame são: ");
            a.setText("sagital, coronal, horizontal (axial) e posterior");
            b.setText("horizontal, coronal, posterior e transverso");
            c.setText("sagital, coronal, horizontal (axial) e oblíquo");
            d.setText("sagital, oblíquo, longitudinal e lateral");
            e.setText("coronal, transverso, horizontal (axial) e sagital");
            alt = R.id.c;
        }
        if (question == 42) {
            questaoTextView.setText("A radiação secundária são raios aleatórios que atingem o filme no momento da realização do exame. O dispositivo utilizado para absorver a maior parte dessa radiação secundária chama-se");
            a.setText("colimador");
            b.setText("cilindro");
            c.setText("cone");
            d.setText("Buck");
            e.setText("grade antidifusora");
            alt = R.id.e;
        }
        if (question == 43) {
            questaoTextView.setText("Vários exames de raios-x exigem angulações do raio central. O instrumento utilizado para medir essas angulações corretamente é ");
            a.setText("o goniômetro");
            b.setText("o espessômetro");
            c.setText("a régua");
            d.setText("o densitômetro");
            e.setText("o micrómetro");
            alt = R.id.a;
        }
        if (question == 44) {
            questaoTextView.setText("Em um exame de Tomografia Computadorizada (TC), a relação entre a velocidade da mesa e a espessura do corte é denominada ");
            a.setText("Voxel");
            b.setText("Pitch");
            c.setText("Pixel");
            d.setText("Matriz");
            e.setText("Imagem");
            alt = R.id.b;
        }
        if (question == 45) {
            questaoTextView.setText("Nos exames de Tomografia Computadorizada (TC), os parâmetros para aquisição de imagens variam para cada estrutura a ser analisada, levando em conta fatores como pitch, campo de visão, espessura do corte. Esse conjunto de fatores pré-estabelecidos na TC é chamado de");
            a.setText("Scout");
            b.setText("Reconstrução");
            c.setText("Escanograma");
            d.setText("Matriz ");
            e.setText("Protocolo");
            alt = R.id.e;
        }
        if (question == 46) {
            questaoTextView.setText("Os exames de Medicina Nuclear são realizados utilizando radiofármacos que são uma combinação de um material radioativo com uma droga farmacêutica. O radiofármaco mais utilizado nesses exames é o tecnécio-99m, pois possui uma meia vida bastante curta. A meia vida do tecnécio-99m é de");
            a.setText("2 horas");
            b.setText("6 horas");
            c.setText("15 horas");
            d.setText("20 horas");
            e.setText("25 horas");
            alt = R.id.b;
        }
        if (question == 47) {
            questaoTextView.setText("A diferença da intensidade de radiação emitida entre as extremidades do tubo de raios-x, conhecida como efeito anódico, pode ser utilizada no exame de");
            a.setText("tórax");
            b.setText("mão");
            c.setText("pé");
            d.setText("fêmur");
            e.setText("joelho");
            alt = R.id.d;
        }
        if (question == 48) {
            questaoTextView.setText("Em um paciente realiza-se um exame de tórax com espessura de 22 cm e a constante (K) do aparelho é igual a 30. Quantos KV deverão ser utilizados nesse exame? ");
            a.setText("52");
            b.setText("65");
            c.setText("74");
            d.setText("78");
            e.setText("80");
            alt = R.id.c;
        }
        if (question == 49) {
            questaoTextView.setText("A fórmula matemática utilizada para achar o KV correto para cada exposição radiográfica é ");
            a.setText("KV=(K+E)x2");
            b.setText("KV=(2xK)+E");
            c.setText("KV=K+(Ex3)");
            d.setText("KV=2xE");
            e.setText("KV=(Ex2)+K");
            alt = R.id.e;
        }
        if (question == 50) {
            questaoTextView.setText("Alguns pacientes apresentam contraindicação para realização de um exame de Ressonância Magnética. Entre os pacientes citados a seguir, qual apresenta contraindicação para esse exame? ");
            a.setText("Paciente alérgico");
            b.setText("Paciente portador de grampo de aneurisma");
            c.setText("Paciente com labirintite");
            d.setText("Paciente com câncer");
            e.setText("Paciente com prótese dentária");
            alt = R.id.b;
        }
        if (question == 51) {
            questaoTextView.setText("A qualidade das imagens é essencial para um bom diagnóstico. As imagens podem ser afetadas pela presença de artefatos. Ruído eletrônico, dobra da imagem, desvio químico e mapeamento incorreto são artefatos gerados nas imagens de");
            a.setText("ressonância magnética");
            b.setText("tomografia computadorizada");
            c.setText("raios-x");
            d.setText("ultrassom");
            e.setText("densitometria óssea");
            alt = R.id.a;
        }
        if (question == 52) {
            questaoTextView.setText("O Gadolínio é um elemento da família dos metais nobres e é o meio de contraste mais utilizado em exames de ");
            a.setText("ultrassom");
            b.setText("tomografia computadorizada");
            c.setText("ressonância magnética");
            d.setText("cintilografia");
            e.setText("raios-x");
            alt = R.id.c;
        }
        if (question == 53) {
            questaoTextView.setText("Em relação aos equipamentos de Teleterapia utilizados em Radioterapia, assinale a alternativa correta. ");
            a.setText("Os equipamentos de raios X superficial ou de ortovoltagem são empregados nos tratamentos de vários tipos de lesões independente da profundidade de infiltração delas. ");
            b.setText("Nos equipamentos de Cobalto-60, a emissão de fótons gama é contínua e independe de o equipamento estar desligado. ");
            c.setText("Os aceleradores lineares não podem gerar fótons de energia maior do que os fótons gerados pelo equipamento de Cobalto-60. ");
            d.setText("Nos aceleradores lineares, nêutrons são acelerados a grandes velocidades em um tubo com vácuo para a obtenção de fótons de raios X. ");
            e.setText("No tratamento com acelerador linear, fótons de alta energia depositam maior dose de radiação na pele e nos tecidos sadios do paciente. ");
            alt = R.id.b;
        }

        if (question == 54) {
            questaoTextView.setText("De acordo com a técnica de tratamento de Braquiterapia, é correto afirmar que ");
            a.setText("a dose de radiação recebida pelo tecido aumenta com o aumento da distância entre o tecido e a fonte de braquiterapia. ");
            b.setText("na braquiterapia o tumor recebe altas doses de radiação, assim como os tecidos sadios vizinhos. ");
            c.setText("as fontes de braquiterapia normalmente permanecem no paciente entre 24 horas e 72 horas. ");
            d.setText("a braquiterapia constitui uma forma de tratamento na qual a fonte de radiação encontra-se a uma determinada distância da área a ser tratada. ");
            e.setText("nos casos de tratamento em que são utilizadas sementes de ouro-198, as fontes não podem permanecer no paciente por mais tempo, devido ao rápido decaimento dessas fontes. ");
            alt = R.id.c;
        }
        if (question == 55) {
            questaoTextView.setText("Sobre o posicionamento dos pacientes nos tratamentos de radioterapia, assinale a alternativa correta. ");
            a.setText("A precisão na reprodutibilidade do posicionamento do paciente não interfere no sucesso do tratamento radioterápico. ");
            b.setText("A imobilização e o posicionamento do paciente nunca interferem na localização de campos de irradiação. ");
            c.setText("O paciente deve ser posicionado de forma a estar confortável e relaxado durante o tratamento. ");
            d.setText("Os dispositivos utilizados para a imobilização do paciente sempre interferem no plano de tratamento por atenuar o feixe de radiação. ");
            e.setText("Dispositivos de imobilização do paciente nunca são confeccionados dentro do próprio serviço de radioterapia. ");
            alt = R.id.c;
        }
        if (question == 56) {
            questaoTextView.setText("Em relação à cavidade abdominal e aos órgãos abdominais, é INCORRETO afirmar que ");
            a.setText("topograficamente o abdome pode ser dividido em seis regiões anatômicas. ");
            b.setText("o peritônio apresenta duas lâminas contínuas: peritônio parietal e visceral. ");
            c.setText("o fígado, o baço e o estômago são exemplos de órgãos intraperitoneais. ");
            d.setText("o baço está localizado no quadrante superior esquerdo do abdome. ");
            e.setText("estruturas anatômicas como rins e ureteres são órgãos retroperitoneais. ");
            alt = R.id.a;
        }
        if (question == 57) {
            questaoTextView.setText("De acordo com a anatomia radiográfica do tórax, assinale a alternativa INCORRETA. ");
            a.setText("A anatomia radiográfica do tórax pode ser dividia em três partes: caixa torácica, sistema respiratório e mediastino. ");
            b.setText("A proeminência vertebral da sétima vértebra cervical e a fúrcula esternal são estruturas de referência topográfica para o posicionamento do paciente. ");
            c.setText("O termo víscera torácica é utilizado para descrever as estruturas dos pulmões e órgãos torácicos alojados no mediastino. ");
            d.setText("O pulmão direito apresenta três lobos e o pulmão esquerdo apenas dois. ");
            e.setText("As glândulas tireoide e paratireoides são estruturas mediastinais. ");
            alt = R.id.e;
        }
        if (question == 58) {
            questaoTextView.setText("Sobre a Tomografia Computadorizada com simulador (CT-Simulador), é correto afirmar que ");
            a.setText("a delimitação do campo de tratamento nunca é feita pelo estudo da região anatômica através do método de tomografia computadorizada. ");
            b.setText("o tomógrafo simulador apresenta um conjunto de lasers independentes e móveis nos eixos X e Z. ");
            c.setText("mediante o uso somente do CT-simulador, a localização do isocentro não é realizada no próprio tomógrafo. ");
            d.setText("as imagens obtidas no CT-Simulador são enviadas para o sistema de planejamento em plataforma JPEG. ");
            e.setText("o CT-Simulador corresponde a um equipamento de tomografia computadorizada que não é adaptado ao planejamento radioterápico. ");
            alt = R.id.b;
        }
        if (question == 59) {
            questaoTextView.setText("Assinale a alternativa INCORRETA sobre os acessórios de imobilização utilizados em radioterapia. ");
            a.setText("As máscaras de acrílico utilizadas para a imobilização da região da cabeça podem afetar a distribuição da dose na região, podendo provocar reações na pele maiores do que as previstas. ");
            b.setText("Imobilizadores pélvicos são muito úteis nos casos da radioterapia conformacional. ");
            c.setText("Os imobilizadores a vácuo não podem ser moldados de acordo com a região anatômica a ser tratada. ");
            d.setText("Pacientes que irradiam a região de cabeça e pescoço devem utilizar o extensor de ombros como acessório, a fim de retirar os ombros da região de incidência do feixe de radiação. ");
            e.setText("O suporte denominado de base em T é utilizado no tratamento de irradiação dos pulmões quando a entrada do feixe de radiação requer campos oblíquos. ");
            alt = R.id.c;
        }
        if (question == 60) {
            questaoTextView.setText("Sobre a anatomia radiográfica do crânio, é correto afirmar que ");
            a.setText("a calota craniana é formada pelos ossos frontal, parietal direito e esquerdo, occiptal e esfenoide. ");
            b.setText("o osso esfenoide apresenta uma depressão central em sua estrutura denominada forame oval. ");
            c.setText("a sutura lambdoide separa o osso frontal dos ossos parietais direito e esquerdo. ");
            d.setText("a base do crânio é formada apenas pelos ossos occiptal, esfenoide e etmoide. ");
            e.setText("os órgãos da audição estão localizados na porção petrosa do osso temporal. ");
            alt = R.id.e;
        }
        if (question == 61) {
            questaoTextView.setText("De acordo com a Radioatividade, assinale a alternativa correta. ");
            a.setText("A emissão não espontânea de partículas ou energia do interior de um núcleo atômico é denominada desintegração nuclear. ");
            b.setText("Os termos desintegração nuclear e decaimento radioativo não são sinônimos. ");
            c.setText("O número de decaimentos por unidade de tempo de uma amostra radioativa é denominado concentração. ");
            d.setText("No decaimento gama, é emitido um fóton de raio gama de energia correspondente à diferença entre dois níveis de energia nuclear. ");
            e.setText("No decaimento alfa, a perda de energia pelo núcleo radioativo ocorre através da emissão de um nêutron. ");
            alt = R.id.d;
        }
        if (question == 62) {
            questaoTextView.setText("Em relação aos raios X, é correto afirmar que ");
            a.setText("os raios X são ondas eletromagnéticas que não são desviadas por campos elétricos e magnéticos. ");
            b.setText("o cátodo é o eletrodo positivo do tubo de raios X. ");
            c.setText("a área do ânodo na qual ocorrem as interações dos elétrons não é denominada alvo. ");
            d.setText("os elétrons são acelerados dentro do tubo de raios X por meio da aplicação de um campo magnético entre os eletrodos presentes no tubo. ");
            e.setText("o material que compõem o cátodo deve apresentar baixo ponto de fusão e alto número atômico. ");
            alt = R.id.a;
        }
        if (question == 63) {
            questaoTextView.setText("De acordo com as técnicas radiológicas da coluna vertebral, é correto afirmar que ");
            a.setText("a incidência radiográfica transoral em anteroposterior é realizada para o estudo do processo odontoide. ");
            b.setText("nos posicionamentos oblíquos anteriores e posteriores da coluna cervical não é possível a visibilização dos forames intervertebrais. ");
            c.setText("a radiografia lateral com o paciente em ortostase não corresponde a uma incidência básica para a rotina de estudo de escoliose. ");
            d.setText("na incidência anteroposterior axial do cóccix o raio central deve incidir angulado 45° no sentido caudal, aproximadamente, cinco centímetros acima da sínfise púbica. ");
            e.setText("as incidências oblíquas anterior e posterior da coluna torácica são consideradas incidências básicas de rotina. ");
            alt = R.id.a;
        }
        if (question == 64) {
            questaoTextView.setText("Sobre as técnicas radiológicas empregadas no estudo do tórax, assinale a alternativa INCORRETA. ");
            a.setText("Na incidência posteroanterior do tórax, o raio central deve ser centralizado no plano mediossagital ao nível da sétima vértebra torácica. ");
            b.setText("Nas radiografias de tórax, é desejável a obtenção de uma longa escala de contraste para que as estruturas vasculares no interior dos pulmões possam ser observadas. ");
            c.setText("A região de hilo pulmonar pode ser observada nas radiografias em perfil do tórax. ");
            d.setText("Nas radiografias de tórax posteroanterior e perfil, deve ser solicitado ao paciente que permaneça em expiração total. ");
            e.setText("No posicionamento do perfil de tórax, o lado esquerdo do paciente deve ficar em contato com o receptor de imagem. ");
            alt = R.id.d;
        }
        if (question == 65) {
            questaoTextView.setText("De acordo com a aplicação da Radioproteção em Braquiterapia, assinale a alternativa correta. ");
            a.setText("A técnica de pós-carregamento remoto aumenta a dose recebida pelos profissionais. ");
            b.setText("Os profissionais podem olhar diretamente para as fontes radioativas sem a implicação de maiores problemas. ");
            c.setText("As fontes utilizadas na Braquiterapia devem ser sempre manuseadas pelos profissionais atrás de blindagens. ");
            d.setText("Não há restrições quanto ao tempo em que visitantes podem ficar perto do paciente submetido a tratamento de Braquiterapia.");
            e.setText("O quarto de tratamento do paciente não necessita ser blindado. ");
            alt = R.id.c;
        }
        if (question == 66) {
            questaoTextView.setText("Assinale a alternativa correta sobre os Acelerados Lineares utilizados em Radioterapia. ");
            a.setText("No canhão de elétrons do equipamento, ocorre a geração dos nêutrons que serão acelerados. ");
            b.setText("Os colimadores não são responsáveis pela definição do campo a ser tratado. ");
            c.setText("A refrigeração do equipamento é feita por meio da circulação de gás carbônico. ");
            d.setText("O Acelerador Linear pode ser utilizado para a produção de raios X de alta energia, assim como para a produção de elétrons. ");
            e.setText("O Acelerador Linear é um exemplo de equipamento empregado na modalidade de tratamento de Braquiterapia. ");
            alt = R.id.d;
        }
        if (question == 67) {
            questaoTextView.setText("Em relação à Radioterapia de Intensidade Modulada (MTR), é INCORRETO afirmar que ");
            a.setText("a técnica de MTR representa uma avançada modalidade de tratamento que possibilita um maior controle de irradiação de tecidos tumorais e uma minimização da dose nos tecidos sadios vizinhos. ");
            b.setText("a complexidade do equipamento de MTR utilizado na administração da dose de radiação destaca-se como uma dificuldade encontrada para a implantação de tal tecnologia na rotina clínica de um Serviço de Radioterapia. ");
            c.setText("a técnica de MTR, apesar de limitar a irradiação de estruturas anatômicas vizinhas ao tumor, não possibilita uma maior homogeneidade de deposição da dose de radiação no interior do volume alvo. ");
            d.setText("um Programa de Controle de Qualidade é necessário para garantir o correto funcionamento do equipamento e, consequentemente, a aplicação de doses adequadas nos pacientes. ");
            e.setText("modalidades de imagem como a Tomografia Computadorizada, Ressonância Magnética e Tomografia por Emissão de Pósitrons podem auxiliar no planejamento 3D do tratamento radioterápico. ");
            alt = R.id.c;
        }
        if (question == 68) {
            questaoTextView.setText("De acordo com o planejamento tridimensional ou conformacional utilizado nos Tratamentos Radioterápicos, assinale a alternativa correta. ");
            a.setText("A radioterapia tridimensional inviabiliza o aumento da dose no volume alvo. ");
            b.setText("Para um planejamento 3D, são requeridas as etapas de pré-simulação, realização do exame, delineamento, determinação do isocentro e planejamento. ");
            c.setText("Na simulação do planeamento tridimensional, não é necessário o uso de imobilizadores que serão utilizados durante o tratamento do paciente. ");
            d.setText("O planejamento tridimensional deve ser realizado somente em um CT-simulador. ");
            e.setText("O planejamento 3D permite a obtenção de arranjo de campos mais complexos, mas não aumenta a precisão do planejamento em relação ao planejamento radioterápico realizado por meio de radiografias ortogonais. ");
            alt = R.id.b;
        }
        if (question == 69) {
            questaoTextView.setText("Assinale a alternativa correta com relação à etapa de Delineamento das estruturas, que é realizada no planejamento tridimensional do Tratamento Radioterápico. ");
            a.setText("Para o delineamento do volume alvo são necessárias somente duas definições: GTV (Volume Tumoral Visível ou Palpável) e CTV (Volume Alvo). ");
            b.setText("O GTV (Volume Tumoral Visível ou Palpável) corresponde a parte do tumor em que há uma maior concentração de células malignas. ");
            c.setText("O desenho do CTV (Volume Alvo) baseia-se em considerações topográficas e anatômicas do paciente considerando o movimento do paciente e dos órgãos. ");
            d.setText("Nos casos de remoção cirúrgica do tumor, o GTV (Volume Tumoral Visível ou Palpável) ainda pode ser definido. ");
            e.setText("O PTV (Volume de Planejamento) não faz parte do delineamento do volume alvo. ");
            alt = R.id.b;
        }
        if (question == 70) {
            questaoTextView.setText("Sobre a anatomia do Sistema Gastrointestinal, é INCORRETO afirmar que ");
            a.setText("as glândulas salivares, o pâncreas, o fígado e a vesícula biliar são órgãos acessórios do Sistema Gastrointestinal. ");
            b.setText("a faringe é dividida em nasofaringe, orofaringe e laringofaringe. ");
            c.setText("o esôfago encontra-se localizado posteriormente à traqueia. ");
            d.setText("a glândula parótida localiza-se abaixo da mandíbula. ");
            e.setText("a junção esofagogástrica também é denominada orifício cardíaco. ");
            alt = R.id.d;
        }
        if (question == 71) {
            questaoTextView.setText("Com relação aos órgãos pélvicos e as estruturas pertencentes ao Sistema Urinário, é correto afirmar que ");
            a.setText("a bexiga urinária, a uretra e os órgãos reprodutores masculinos são órgãos retroperitoniais. ");
            b.setText("os ureteres situam-se na superfície posterior do músculo psoas maior. ");
            c.setText("o plexo venoso vesical circunda as extremidades superiores dos ureteres. ");
            d.setText("a estrutura do reto constitui a porção abdominal do tubo digestório. ");
            e.setText("pelve renal é a nomenclatura associada à expansão afunilada e achatada da extremidade superior do ureter. ");
            alt = R.id.e;
        }
        if (question == 72) {
            questaoTextView.setText("De acordo com os equipamentos de raios X utilizados em Radioterapia, é correto afirmar que ");
            a.setText("independente do caso clínico a ser tratado, são utilizados feixes de raios X de mesma energia. ");
            b.setText("na Terapia Superficial a distância foco-filme e/ou foco/ pele é menor ou igual a dois centímetros. ");
            c.setText("a Terapia de Contato também é denominada ortovoltagem. ");
            d.setText("na Terapia Profunda a distância foco-superfície de tratamento varia de um a dez centímetros. ");
            e.setText("a Terapia Profunda é empregada no tratamento de tumores de pele e na prevenção de queloides. ");
            alt = R.id.e;
        }
        if (question == 73) {
            questaoTextView.setText("Assinale a alternativa INCORRETA em relação aos suportes de posicionamento empregados em Radioterapia. ");
            a.setText("Travesseiros e colchões de espuma são utilizados para auxiliar no conforto de pacientes que apresentam dor e metástases ósseas. ");
            b.setText("Na base em T, utilizada para irradiação da região pulmonar, os braços do paciente devem ser posicionados para cima. ");
            c.setText("O triângulo de apoio pode ser posicionado sob os joelhos do paciente, quando este estiver na posição de decúbito ventral. ");
            d.setText("A rampa de mama é um acessório que permite que o braço do paciente fique em uma abertura perpendicular ao campo de tratamento. ");
            e.setText("O extensor de ombros é utilizado para auxiliar no posicionamento de pacientes que irradiam as regiões de cabeça e pescoço. ");
            alt = R.id.c;
        }
        if (question == 74) {
            questaoTextView.setText("Assinale a alternativa correta sobre as imagens tomográficas. ");
            a.setText("No processo de janelamento das imagens, os valores de número CT, expressos na escala de Hounsfield, não são alterados. ");
            b.setText("O número máximo de tons de cinza que pode ser exibido no monitor do equipamento é denominado nível de janela ou window level (WL). ");
            c.setText("A largura da janela ou window width (WW) representa o centro da escala de tons de cinza. ");
            d.setText("Uma escala longa de tons de cinza pode ser obtida com larguras de janelas (WW) maiores. ");
            e.setText("O janelamento para estruturas ósseas é obtido por meio da inclusão de baixos valores de número CT na escala de contraste. ");
            alt = R.id.d;
        }
        if (question == 75) {
            questaoTextView.setText("Sobre o Código de Ética dos Profissionais das Técnicas Radiológicas, definido nos termos da Resolução n° 15, de 12 de dezembro de 2011, do Conselho Nacional de Técnicos em Radiologia (CONTER), é correto afirmar que ");
            a.setText("o Código de Ética Profissional expõe os princípios éticos, os direitos e deveres, mas não trata das condutas necessárias para a prática da profissão de Tecnólogo, Técnico e Auxiliar de Radiologia. ");
            b.setText("é vedado ao profissional Tecnólogo, Técnico e Auxiliar de Radiologia fornecer ao cliente/paciente informações não específicas de sua formação. ");
            c.setText("o profissional de radiologia não deve se posicionar em situações de erros técnicos de demais profissionais da área. ");
            d.setText("os profissionais Tecnólogos, Técnicos e Auxiliares de Radiologia não são obrigados a prestar depoimentos em processo administrativo ou judicial sobre fatos que envolvam seus colegas e de que tenham conhecimento em razão do ambiente profissional. ");
            e.setText("no desempenho de suas funções profissionais, o Tecnólogo, o Técnico e o Auxiliar de radiologia podem executar técnicas radiológicas, radioterápicas, nuclear e industrial sem que haja requisição. ");
            alt = R.id.b;
        }
        if (question == 76) {
            questaoTextView.setText("De acordo com a Portaria n° 453, de 1 de junho de 1998, da Secretaria de Vigilância Sanitária do Ministério da Saúde, é correto afirmar que ");
            a.setText("não compete aos órgãos de Vigilância Sanitária dos Estados, do Distrito Federal e dos Municípios o licenciamento dos estabelecimentos que possuem os raios X diagnósticos. ");
            b.setText("a Portaria n°453/98 não atende à política nacional de proteção à saúde. ");
            c.setText("a limitação de doses individuais e a otimização da proteção radiológica são os únicos princípios básicos que regem a Portaria n°453/98. ");
            d.setText("a limitação de doses individuais não corresponde a um princípio de proteção radiológica. ");
            e.setText("toda a exposição que não pode ser justificada é proibida pela Portaria n°453/98. ");
            alt = R.id.e;
        }
        if (question == 77) {
            questaoTextView.setText("Assinale a alternativa INCORRETA de acordo com os Princípios e Diretrizes do Sistema Único de Saúde (SUS). ");
            a.setText("Os princípios doutrinários que regem o SUS se baseiam nos preceitos constitucionais. ");
            b.setText("A Universalidade não está entre as doutrinas e princípios do SUS. ");
            c.setText("A Resolubilidade e a Descentralização são princípios que regem a organização do SUS. ");
            d.setText("A contratação de serviços privados é permitida mediante a insuficiência do setor público. ");
            e.setText("O Princípio da Equidade assegura que todo cidadão é igual perante o SUS.");
            alt = R.id.b;
        }
        if (question == 78) {
            questaoTextView.setText("Os operadores de equipamentos de raios x devem entender necessariamente a relação entre os fatores geométricos e a qualidade das imagens produzidas durante o exercício de suas atividades. Acerca deste assunto, a melhor combinação entre os fatores geométricos para produzir imagens de boa qualidade, com mais detalhes e melhor resolução é ");
            a.setText("ponto focal maior, distância foco filme menor (DFOFI) e filamento menor (mA). ");
            b.setText("ponto focal menor, distância foco filme maior e distância objeto filme maior (DOF). ");
            c.setText("ponto focal menor, distância foco filme maior (DFOFI) e distância objeto filme menor (DOF). ");
            d.setText("ponto focal maior, distância foco filme menor e distância objeto filme maior (DOF). ");
            e.setText("ponto focal maior, distância foco filme menor e filamento maior. ");
            alt = R.id.c;
        }
        if (question == 79) {
            questaoTextView.setText("Para a realização de um exame radiográfico, foram usados 80 Kvp e 40mAs. Sendo o filamento 40 mA e o tempo 1 segundo. Sabe-se que o fator mAs é a exposição ou dose de radiação. Os mesmos 40mAs podem ser obtidos a partir do uso do filamento 400 mA e tempo de 0,1 segundo, estes produzirão a mesma exposição. Qual das situações apresentadas minimizará a movimentação do paciente, porém a imagem terá perda nos detalhes? ");
            a.setText("Em ambas as situações a dose de radiação será a mesma, portanto as chances de movimentos serão as mesmas. ");
            b.setText("A dose de radiação será a mesma, porém o movimento e perda de detalhes poderão ocorrer na segunda situação. ");
            c.setText("O movimento poderá ocorrer quando selecionado o filamento 40mA e 1 segundo perdendo detalhes na imagem. ");
            d.setText("Na segunda situação, o paciente não terá chances de movimentar-se, porém a imagem perderá detalhes. ");
            e.setText("Na primeira situação, o paciente terá chances de movimentar-se, porém a imagem perderá detalhes. ");
            alt = R.id.d;
        }
        if (question == 80) {
            questaoTextView.setText("Referente ao ponto focal no tubo de raios, assinale a alternativa INCORRETA. ");
            a.setText("É usado ponto focal menor quando necessitamos de imagens médicas com mais detalhes e boa resolução. ");
            b.setText("Representa a área de incidência ou menor ponto onde os elétrons atingem o ânodo e são produzidos os raios x. ");
            c.setText("É a região de choque dos elétrons e normalmente utilizamos pontos focais maiores quando precisamos de aquisições de imagens médicas com poucos detalhes e baixa resolução com tempo maior de exposição. ");
            d.setText("É a região de choque dos elétrons e normalmente utiliza-se pontos focais maiores quando precisamos de aquisições de imagens médicas com pouco detalhes e baixa resolução com tempo menor de exposição. ");
            e.setText("É usado ponto focal maior quando necessitamos de imagens médicas com menos detalhes e pouca resolução. ");
            alt = R.id.c;
        }
        if (question == 81) {
            questaoTextView.setText("Os equipamentos de raios x, em geral, possuem 2 tipos de ânodos: o fixo ou o giratório. Referente ao assunto, assinale a alternativa correta. ");
            a.setText("O ânodo fixo é utilizado na radiologia convencional por ter maior facilidade de dissipar o calor produzido durante o choque dos elétrons no alvo, fornecendo assim maior área de impacto aos elétrons. ");
            b.setText("O ânodo giratório é utilizado na radiologia convencional por facilitar a dissipação do calor produzido durante o choque dos elétrons no alvo, fornecendo assim maior área de impacto aos elétrons no alvo de tungstênio. ");
            c.setText("O ânodo giratório é utilizado na radiologia convencional por facilitar dissipação do calor produzido durante o choque dos elétrons no alvo, fornecendo assim menor área de impacto aos elétrons no alvo de tungstênio. ");
            d.setText("O ânodo giratório é utilizado na radiologia convencional por facilitar dissipação do calor produzido durante o choque dos elétrons no alvo, fornecendo assim maior área de impacto aos elétrons no filamento do tubo de raios x. ");
            e.setText("O ânodo fixo é utilizado na radiologia odontológica por ter maior facilidade de dissipar o calor produzido durante o choque dos elétrons no alvo, fornecendo assim maior área de impacto aos elétrons e utilização de energias maiores.");
            alt = R.id.b;
        }
        if (question == 82) {
            questaoTextView.setText("Um dos fenômenos que tem relação com a qualidade da imagem em radiologia é o efeito anódico. Sobre o efeito anódico, assinale a alternativa correta. ");
            a.setText("Durante a instalação de um equipamento de raios x na sala, o pólo negativo ânodo deve estar voltado para a estativa, assim sendo aproveitamos melhor este fenômeno durante os exames radiográficos. ");
            b.setText("Durante a instalação de um equipamento de raios x na sala, o pólo negativo cátodo deve estar oposto à estativa, assim sendo aproveitamos melhor este fenômeno durante os exames radiográficos. ");
            c.setText("Durante a instalação de um equipamento de raios x na sala, o pólo negativo cátodo deve estar voltado para a estativa, assim sendo aproveitamos melhor este fenômeno durante os exames radiográficos. ");
            d.setText("Durante a instalação de um equipamento de raios x na sala, os pólos ânodo e cátodo não tem relação com imagem e a estativa, assim sendo, não influenciam nos exames radiográficos. ");
            e.setText("É o fenômeno em que a intensidade da radiação decresce conforme se aumenta a distância da fonte emissora de radiação e, portanto, a parte mais espessa deve ser colocada do lado do ânodo. ");
            alt = R.id.c;
        }
        if (question == 83) {
            questaoTextView.setText("Assinale a afirmação correta em relação às aplicações das seguintes radiações. ");
            a.setText("Na área da saúde para diagnóstico: raios X e gama. ");
            b.setText("Na área da saúde para terapia: raios X, gama e alfa. ");
            c.setText("Na área da indústria: raios-x, gama e alfa. ");
            d.setText("Na área da pesquisa: raios-x, alfa e gama. ");
            e.setText("Na área da agricultura: raios-x, beta e gama. ");
            alt = R.id.a;
        }
        if (question == 84) {
            questaoTextView.setText("Analise as assertivas e assinale a alternativa que aponta as correta, sobre o uso dos écrans utilizados na radiologia. \n\rI. Aumentam a produção dos raios x e melhoram a qualidade da imagem produzida.\n\rII. Reduzem a dose de radiação durante os exames.\n\rIII. Durante a exposição, ocorre a fosforescência que transforma raios X em luz visível.\n\rIV. Durante a exposição, ocorre a fluorescência que transforma raios X em luz visível.\n\rV. Aumentam a vida útil das ampolas de raios x.  ");
            a.setText("Apenas I, II e V ");
            b.setText("Apenas I, II, IV e V. ");
            c.setText("Apenas II, III e V. ");
            d.setText("Apenas II, III, IV e V. ");
            e.setText("Apenas II, IV e V. ");
            alt = R.id.e;
        }
        if (question == 85) {
            questaoTextView.setText("A exposição a fontes de radiação ionizante pode induzir o surgimento de dano ao tecido biológico em indivíduos. Os efeitos da radiação que ocorrem no indivíduo exposto e precisam de limiar de dose denominam-se ");
            a.setText("Somáticos. ");
            b.setText("Genéticos. ");
            c.setText("Determinísticos. ");
            d.setText("De interação ");
            e.setText("Estocásticos. ");
            alt = R.id.c;
        }
        if (question == 86) {
            questaoTextView.setText("As exposições médicas ");
            a.setText("são exposições de indivíduos em decorrência do seu trabalho. ");
            b.setText("são exposições de pacientes em decorrência de exames, e de acompanhantes que ajudam a conter o paciente. ");
            c.setText("são exposições de médicos envolvidos em atividades que fazem o uso das radiações ionizantes. ");
            d.setText("são exposições somente de pacientes em decorrência de solicitações médicas a exames radiológicos. ");
            e.setText("são as exposições de todos os profissionais envolvidos em atividades com radiação ionizantes.");
            alt = R.id.b;
        }
        if (question == 87) {
            questaoTextView.setText("De acordo com a norma CNEN-NN-3.01 “Diretrizes Básicas de Proteção Radiológica”, o limite anual de doses para extremidades em indivíduos ocupacionalmente expostos é igual a ");
            a.setText("150 mSv/ano ");
            b.setText("50 mSv/ano ");
            c.setText("20 mSv/ano ");
            d.setText("1 mSv/ano ");
            e.setText("500 mSv/ano ");
            alt = R.id.e;
        }
        if (question == 88) {
            questaoTextView.setText("As bombas de cobalto-60 e Irídio-192 são materiais que possuem radioatividade, emitem radiação constantemente, independente da nossa vontade, e são usadas para radioterapia. Em relação à fonte selada de radiação, é correto afirmar que ");
            a.setText("não há contato físico entre o material radioativo e ambiente, estas oferecem riscos de irradiação e contaminação. ");
            b.setText("a fonte está fechada dentro de um invólucro robusto que impede o escape de material radioativo, nessas condições existe o risco de contaminação e de irradiação. ");
            c.setText("o material radioativo está sob a forma líquida ou de pó em recipientes abertos e encontra-se exposto ao contato. ");
            d.setText("ela apresenta o risco de irradiação e contaminação, como os aparelhos de raios x diagnósticos. ");
            e.setText("a fonte está fechada dentro de um invólucro robusto que impede o escape de material radioativo que, em condições normais, apresenta apenas o risco de irradiação. ");
            alt = R.id.e;
        }
        if (question == 89) {
            questaoTextView.setText("A radioproteção se baseia em princípios e diretrizes conforme a recomendação das normas da Comissão Nacional de Energia Nuclear (CNEN). O princípio que rege todos os princípios da radioproteção é a ");
            a.setText("justificação da prática e das exposições em atividades que usem radiação ionizante. ");
            b.setText("otimização da proteção radiológica pensando em doses menores. ");
            c.setText("limitação individual de doses equivalentes e efetivas para profissionais. ");
            d.setText("prevenção de acidentes. ");
            e.setText("ALARA. ");
            alt = R.id.e;
        }
        if (question == 90) {
            questaoTextView.setText("A anatomia radiográfica visualizada na projeção axial (súpero inferior) de ombro demonstra as seguintes estruturas: ");
            a.setText("acrômio, clavícula, processo coronoide, colo cirúrgico, trocanter maior, trocanter menor, cabeça do úmero e espinha da escápula. ");
            b.setText("acrômio, clavícula, processo coronoide, trocanter maior, trocanter menor, cabeça do úmero e espinha da escápula. ");
            c.setText("acrômio, clavícula, processo coracoide, colo anatômico, cavidade glenoide, tubérculo maior, cabeça do úmero e espinha da escápula. ");
            d.setText("acrômio, clavícula, processo coronoide, cavidade glenoide, trocanter maior, trocanter menor, cabeça do úmero e corpo da escápula. ");
            e.setText("acrômio, clavícula, processo coracoide, cavidade glenoide, tubérculo maior, sulco intertubercular, tubérculo menor, cabeça do úmero e espinha da escápula. ");
            alt = R.id.e;
        }
        if (question == 91) {
            questaoTextView.setText("A anatomia radiográfica visualizada na projeção ínferosuperior da patela apresenta as seguintes estruturas: ");
            a.setText("tubérculos da eminência intercondilar, fíbula, epicôndilo medial do fêmur, epicôndilo lateral do fêmur e patela. ");
            b.setText("côndilo medial da tíbia, côndilo lateral da tíbia e patela. ");
            c.setText("tubérculos da eminência intercondilar, tíbia, côndilo medial do fêmur, côndilo lateral do fêmur e patela. ");
            d.setText("tubérculos da eminência intercondilar, tíbia, côndilo medial do fêmur, côndilo lateral do fêmur e patela. ");
            e.setText("côndilo medial do fêmur, côndilo lateral do fêmur e patela. ");
            alt = R.id.e;
        }
        if (question == 92) {
            questaoTextView.setText("Das estruturas apresentadas a seguir, assinale a alternativa que apresenta as que fazem parte do diencéfalo. ");
            a.setText("Metatálamo, subtálamo e mesencéfalo. ");
            b.setText("Hipotálamo, tálamo e epitálamo. ");
            c.setText("Tálamo, mesencéfalo e giro do cíngulo. ");
            d.setText("Epitálamo, hipófise e giro do cíngulo. ");
            e.setText("Hipotálamo, tálamo e hipófise.");
            alt = R.id.b;
        }
        if (question == 93) {
            questaoTextView.setText("O espaço localizado entre a dura-máter e o periósteo, utilizado para aplicação de anestesia, é denominado ");
            a.setText("subaracnoideo. ");
            b.setText("peridural. ");
            c.setText("subdural. ");
            d.setText("subaracnoideo e subdural. ");
            e.setText("corticoespinal. ");
            alt = R.id.b;
        }
        if (question == 94) {
            questaoTextView.setText("O esqueleto axial é composto de 80 ossos e o esqueleto apendicular é composto por 126 ossos. Em relação ao assunto, analise as assertivas e assinale a alternativa que aponta a(s) correta(s). \n\r I. Os ossos da coluna sacral e cóccix estão localizados no esqueleto apendicular.\n\r II. Os ossos como a sínfise púbica, occipital e esterno pertencem ao esqueleto axial. \n\r III. Os ossos do carpo da fileira proximal são escafoides, semilunar piramidal e pisiforme. \n\r IV. Os ossos do carpo da fileira distal são escafoides, semilunar piramidal e pisiforme.  ");
            a.setText("Apenas II e III. ");
            b.setText("Apenas I. ");
            c.setText("Apenas III e IV. ");
            d.setText("Apenas III. ");
            e.setText("Apenas IV. ");
            alt = R.id.d;
        }
        if (question == 95) {
            questaoTextView.setText("A radioterapia de intensidade modulada (da sigla em inglês IMRT - Intensity- Modulated Radiation Therapy ) é a forma mais nova de radioterapia 3D. A IMRT algumas vezes é chamada de “pintura da dose”. Referente ao assunto, assinale a alternativa correta. ");
            a.setText("Na IMRT, o tratamento é associado ao tratamento primário para pacientes com doença localizada. ");
            b.setText("Na IMRT, o tratamento associado ao tratamento secundário para pacientes com metástases. ");
            c.setText("Na IMRT, a fonte de radiação situa-se próximo do paciente, tal como um feixe de raios x. ");
            d.setText("A IMRT é uma terapia adjuvante realizada antes da cirurgia curativa. ");
            e.setText("A IMRT se difere da radioterapia adaptada pelo fato de liberar doses diferentes em cada microfeixe para formar cada um dos campos adaptados. ");
            alt = R.id.e;
        }
        if (question == 96) {
            questaoTextView.setText("Os acessórios de imobilização em radioterapia são fundamentais durante as sessões de terapia. Os suportes têm por objetivo posicionar a coluna cervical de acordo com cada proposta de tratamento. São construídos com formatos padronizados e identificados por letras. O enunciado refere-se a ");
            a.setText("máscaras termoplásticas. ");
            b.setText("suportes para cabeça e pescoço. ");
            c.setText("retrator de ombros. ");
            d.setText("breast board. ");
            e.setText("belly board.");
            alt = R.id.b;
        }
        if (question == 97) {
            questaoTextView.setText("Sobre as técnicas de tratamento e posicionamento de pacientes na radioterapia, é correto afirmar que ");
            a.setText("a radioterapia utiliza a combinação de campos para irradiar o volume alvo com base na dose prescrita pelo radioterapeuta, minimizando os danos aos tecidos sadios. Já o posicionamento incorreto pode acarretar no comprometimento dos resultados esperados, podendo também causar sérios danos ao paciente, como a subdosagem. ");
            b.setText("a radioterapia utiliza a combinação de campos para irradiar o volume alvo com base na dose prescrita pelo radioterapeuta, minimizando os danos aos tecidos sadios. Já o posicionamento incorreto pode acarretar comprometimento dos resultados esperados podendo também causar sérios danos ao paciente como a subdosagem e a sobredosagem. ");
            c.setText("a radioterapia utiliza a combinação de campos para irradiar o volume alvo com base na dose prescrita pelo radioterapeuta, minimizando os danos aos tecidos sadios. Já o posicionamento incorreto pode acarretar no comprometimento dos resultados esperados, podendo também causar sérios danos ao paciente, como a sobredosagem. ");
            d.setText("a radioterapia de intensidade modulada é igual a radioterapia adaptada, pelo fato de liberar doses diferentes em cada microfeixe para formar cada um dos campos adaptados. ");
            e.setText("a IMRT se difere da radioterapia adaptada pelo fato de serem liberadas doses iguais em cada feixe para formar cada um dos campos adaptados. ");
            alt = R.id.b;
        }
        if (question == 98) {
            questaoTextView.setText("A fonte de radiação situa-se distante do tumor do paciente, tal como um feixe de raios x. O enunciado refere-se à ");
            a.setText("braquiterapia. ");
            b.setText("localização. ");
            c.setText("radioterapia adaptada. ");
            d.setText("teleterapia. ");
            e.setText("curva de isodose. ");
            alt = R.id.d;
        }
        if (question == 99) {
            questaoTextView.setText("Em relação aos princípios e diretrizes do Sistema Único de Saúde (SUS) referente ao princípio doutrinário da equidade, assinale a alternativa correta. ");
            a.setText("Este princípio prega o acesso de todas as pessoas aos serviços na área da saúde pelo estado, independente de sexo, raça, ocupação, classe social ou qualquer outra característica individual e social. ");
            b.setText("Este princípio diz respeito à justiça social, sem vinculação de privilégio a nenhuma pessoa. ");
            c.setText("Este princípio representa, para alguns estudiosos, um dos maiores avanços na área da saúde, levando em conta todas as necessidades do indivíduo ou grupo. ");
            d.setText("Este princípio prega o acesso de todas as pessoas aos serviços na área da saúde pelo estado, município e distrito federal, dependendo da característica individual e social. ");
            e.setText("Este princípio prega a universalização no atendimento às pessoas aos serviços na área da saúde. ");
            alt = R.id.b;
        }
        if (question == 100) {
            questaoTextView.setText("Sobre a vigilância sanitária, assinale a alternativa correta. ");
            a.setText("É um conjunto de ações capaz de eliminar, diminuir ou prevenir riscos a saúde e de intervir nos problemas sanitários decorrentes do meio ambiente, da produção e circulação de bens e da prestação de serviços de interesses da saúde. ");
            b.setText("É um conjunto de ações capaz de prevenir riscos à saúde e de intervir nos problemas sanitários decorrentes do meio ambiente, da produção e circulação de bens e da prestação de serviços de interesses da saúde. ");
            c.setText("É um conjunto de ações capaz de eliminar riscos à saúde e de intervir nos problemas sanitários decorrentes do meio ambiente e da prestação de serviços de interesses da saúde. ");
            d.setText("É um conjunto de ações capaz de diminuir ou prevenir riscos à saúde decorrentes do meio ambiente, da produção e circulação de bens e da prestação de serviços sem interesses da saúde. ");
            e.setText("É um conjunto de ações capaz de erradicar riscos à saúde, sem intervir nos problemas sanitários decorrentes do meio ambiente, da produção e circulação de bens e da prestação de serviços de interesses da saúde. ");
            alt = R.id.a;
        }
        return alt;
    }
}

