package com.lasige.roteiroentremares.util;

import com.lasige.roteiroentremares.data.model.EspecieAvencas;
import com.lasige.roteiroentremares.data.model.EspecieRiaFormosa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiaDeCampoContent {
    public static final List<EspecieAvencas> dataEspeciesAvencas = Arrays.asList(
            new EspecieAvencas(
                    "Esponja vermelha",
                    "Hymeniacidon sanguinea",
                    "Esponjas (Porifera)",
                    "As esponjas estão entre os animais mais simples. Não possuem tecidos verdadeiros, nem músculos, sistema nervoso, ou órgãos internos.\n" +
                            "Estão muito próximos de uma colónia, pois cada célula alimenta-se por si própria. Têm forma e cor muito variável e vivem fixas ao substrato.",
                    "São filtradores (alimentam-se de pequenas partículas orgânicas flutuantes)",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    8,
                    new ArrayList<String>(Arrays.asList("img_guiadecampo_esponjavermelha_1")),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Tomate-do-mar",
                    "Actinia equina",
                    "Cnidaria",
                    "Corpo dividido em 3 partes: tentáculos, coluna e base, onde se encontra o pé da base que se fixa à superfície duras como rocha, pedras ou outras superfícies.\n" +
                            "Ocorre no intertidal, sobretudo no mediolitoral. Estas espécies também podem viver em profundidades até 20m.\n" +
                            "O pé pode ter até 5cm de diâmetro, coluna lisa e pode apresentar uma coloração verde, vermelha ou castanha.",
                    "Alimenta-se de diversos organismos como moluscos, bivalves, insectos, caracóis e lesmas do mar, entre outros.",
                    "Consegue sobreviver fora de água e nestas condições recolhe os seus tentáculos para dentro do corpo, evitando a perda de água.",
                    "Estes organismos usam os tentáculos para capturar camarões, juvenis de peixe e outros pequenos animais, dos quais se alimentam. \n" +
                            "Os tentáculos são armados com muitos cnidócitos, que são células de defesa e também um meio de capturar presas. Os cnidócitos são uma espécie de \"chicotes\", também chamados de nematocistos, que contêm uma pequena vesícula cheia de veneno e um sensor; quando o sensor é tocado, ativa o \"chicote\" que crava na presa e injeta o veneno. O veneno serve para paralisar a presa. O veneno é altamente tóxico para os pexes e crustáceos, que são a presa natural das anémonas \n" +
                            "Atenção que esta substância pode causar alergia na nossa pele, especialmente no caso das anémonas.",
                    false,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    7,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_tomatedomar_1",
                            "img_guiadecampo_tomatedomar_2",
                            "img_guiadecampo_tomatedomar_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Anémona",
                    "Anemonia sulcata",
                    "Cnidaria",
                    "Apresenta grandes tentáculos com uma coloração cinzento-acastanhados ou verde-vivo. As anémonas que têm a tonalidade verde apresentam, nas pontas dos tentáculos, uma cor purpura. A coluna tem uma coloração avermelhada ou cinzento-acastanhada.\n" +
                            "Presentes no intertidal, sobretudo no infralitoral e também em poças de maré.",
                    "Sendo carnívoras alimentam-se de juvenis de peixes, crustáceos, plâncton.",
                    "Ao contrário do tomate-do-mar, as anémonas não retraem os tentáculos para o interior do seu corpo, estando mais expostas à secura quando se encontram na maré baixa, nesta situação, as anémonas reduzem a superfície corporal exposta.",
                    "Estes organismos usam os tentáculos para capturar camarões, juvenis de peixe e outros pequenos animais, dos quais se alimentam. \n" +
                            "Os tentáculos são armados com muitos cnidócitos, que são células de defesa e também um meio de capturar presas. Os cnidócitos são uma espécie de \"chicotes\", também chamados de nematocistos, que contêm uma pequena vesícula cheia de veneno e um sensor; quando o sensor é tocado, ativa o \"chicote\" que crava na presa e injeta o veneno. O veneno serve para paralisar a presa. O veneno é altamente tóxico para os peixes e crustáceos, que são a presa natural das anémonas \n" +
                            "Atenção que esta substância pode causar alergia na nossa pele, especialmente no caso das anémonas.",
                    false,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    7,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_anemona_1",
                            "img_guiadecampo_anemona_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Barroeira",
                    "Sabellaria alveolata",
                    "Anelideos (Poliquetas)",
                    "Corpo alongado e geralmente dividido em segmentos ou anéis. Cada segmento apresenta apêndices laterais, constituídos por pequenos pelos rígidos chamados sedas.",
                    "Dentro deste grupo, temos animais sedentários que vivem dentro de tubos e se alimentam por filtração de partículas suspensas na água, e animais móveis , que podem ser carnívoros, herbívoros ou comedores de depósitos seletivos.",
                    "Abrigam-se em tubos de areia, muco ou calcário, que constroem.",
                    "A Sabellaria alveolata, também conhecida por Barroeira, constrói tubos de areia, ou de fragmentos de concha, com a ajuda de um muco produzido pelo organismo, para se abrigar. Esses tubos, que são densamente agregados, formam um padrão em favo de mel e podem formar recifes com vários metros de diâmetro.",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    6,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_barroeira_1"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_barroeira_1"
                    )),
                    ""
            ),
            new EspecieAvencas(
                    "Eulalia",
                    "Eulalia viridis",
                    "Anelideos (Poliquetas)",
                    "Corpo alongado e geralmente dividido em segmentos ou anéis. Cada segmento apresenta apêndices laterais, constituídos por pequenos pelos rígidos chamados sedas.",
                    "Dentro deste grupo, temos animais sedentários que vivem dentro de tubos e se alimentam por filtração de partículas suspensas na água, e animais móveis , que podem ser carnívoros, herbívoros ou comedores de depósitos seletivos.",
                    "Vivem normalmente enterrados na areia ou lodo.",
                    "",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    6,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_eulalia_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Camarão-branco-legítimo",
                    "Palaemon serratus",
                    "Crustáceos (Arthropoda)",
                    "Dentre as principais características, dos crustáceos salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen (mas há exceções). Os camarões possuem o corpo protegido por uma cutícula calcificada. Coloração acinzentada a rosa-pálido com estrias escuras.\n" +
                            "Está presente no mediolitoral, no entanto, também pode ser encontrada até aos 50m de profundidade. Espécie de actividade nocturna, pode ser encontrado nas poças de maré.",
                    "Espécie omnívora, alimenta-se de algas, matéria orgânica em decomposição, pequenos crustáceos, moluscos e outros pequenos invertebrados. Por vezes também são canibais (comem a sua própria espécie).",
                    "Toleram grandes variações de salinidade e temperatura; reduzem a atividade; fazem osmorregulação (equilíbrio entre a quantidade de água e sais minerais no corpo); permanecendo em aglomerados de algas, debaixo de pedras ou fendas.",
                    "Tanto no caso do camarão como do caranguejo, as fêmeas transportam os ovos  no seu próprio corpo. No caso dos camarões, as fêmeas transportam-nos durante 2 a 4 meses, e como o corpo é transparente, podemos observar os ovos a olho nú. \n" +
                            "No caso dos caranguejos, os ovos são transportados pela fêmea, presos às sedas dos pleópodes (apêndices transformados, que se encontram na parte dobrada do abdómen). Durante esse tempo a fêmea limpa e areja a massa de ovos. Nesta espécie, consegue-se distinguir o sexo do animal através da forma do abdómen: triangular nos machos (imagem de cima) versus arredondado nas fêmeas (imagem de baixo)",
                    true,
                    false,
                    true,
                    false,
                    false,
                    true,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_camarao_1"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_caranguejo_1"
                    )),
                    ""
            ),
            new EspecieAvencas(
                    "Caranguejo-preto",
                    "Pachygrapsus marmoratus",
                    "Crustáceos (Arthropoda)",
                    "Dentre as principais características dos crustáceos, salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen. Os caranguejos possuem uma carapaça calcária, que protege o corpo. Pequeno caranguejo com uma carapaça quadrada de 22 a 36 mm de comprimento. Apresenta uma coloração violeta-escuro com um marmoreado amarelado. Apresenta 3 dentes em cada lado da carapaça.\n" +
                            "O polvo é o predador desta espécie.\n" +
                            "Presente no mediolitoral.",
                    "Omnívoro, alimenta-se de algas e vários animais como mexilhão e lapas.",
                    "Toleram grandes variações de salinidade e temperatura; reduzem a atividade, permanecendo em aglomerados de algas, debaixo de pedras ou fendas.",
                    "Tanto no caso do camarão como do caranguejo, as fêmeas transportam os ovos  no seu próprio corpo. No caso dos camarões, as fêmeas transportam-nos durante 2 a 4 meses, e como o corpo é transparente, podemos observar os ovos a olho nú. \n" +
                            "No caso dos caranguejos, os ovos são transportados pela fêmea, presos às sedas dos pleópodes (apêndices transformados, que se encontram na parte dobrada do abdómen). Durante esse tempo a fêmea limpa e areja a massa de ovos. Nesta espécie, consegue-se distinguir o sexo do animal através da forma do abdómen: triangular nos machos (imagem de cima) versus arredondado nas fêmeas (imagem de baixo)",
                    true,
                    false,
                    true,
                    false,
                    true,
                    false,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_caranguejo_1",
                            "img_guiadecampo_caranguejo_2"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_caranguejo_1"
                    )),
                    ""
            ),
            new EspecieAvencas(
                    "Navalheira",
                    "Necora puber",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. Dentre as principais características, salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen (mas há exceções). Os apêndices são bifurcados, e na sua maioria possuem uma carapaça, placas calcárias, ou uma cutícula calcificada, que protege parte ou todo o corpo.\nEmbora esta espécie não seja típica da zona entre-marés, utiliza com frequência estas plataformas como abrigo, pelo que pode ser observado na franja do infralitoral, em abrigos da rocha. É uma espécie comercializada.",
                    "Omnívoro (alimenta-se de bivalves, poliquetas, algas).",
                    "",
                    "",
                    true,
                    false,
                    true,
                    false,
                    true,
                    false,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_navalheira_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Cracas pequenas",
                    "Chthamalus spp",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                            "Presentes no mediolitoral.",
                    "Filtradores (alimentam-se de partículas em suspensão na água)",
                    "Fecham-se no interior das placas",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cracaspequenas_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Cracas grandes",
                    "Balanus sp",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                            "Presentes no mediolitoral.",
                    "Filtradores (alimentam-se de partículas em suspensão na água)",
                    "Fecham-se no interior das placas",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cracasgrandes_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Percebes",
                    "Pollicipes pollicipes",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.",
                    "Filtradores (alimentam-se de partículas em suspensão na água)",
                    "Fecham-se no interior das placas",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    false,
                    5,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_percebes_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Mexilhão",
                    "Mytilus galloprovincialis",
                    "Moluscos (Mollusca)",
                    "Corpo mole mas protegido por uma concha, bivalve.\n" +
                            "Presente no mediolitoral e no infralitoral.",
                    "Filtradores (de pequenas partículas orgânicas flutuantes, plâncton, e pequenas algas - fitoplâncton).",
                    "As duas valvas protetoras fecham-se para armazenar água. Fixam-se firmemente às rochas, através de fibras chamadas bissos.",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    4,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_mexilhao_1",
                            "img_guiadecampo_mexilhao_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Lapa",
                    "Patella spp",
                    "Moluscos (Mollusca)",
                    "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, com a forma de disco.\n" +
                            "Presente no mediolitoral.",
                    "Herbívoro (raspa as algas)",
                    "Fixam-se firmemente às rochas, e escavam uma pequena cavidade, à qual se ajusta perfeitamente, impedindo a perda de água.",
                    "Apesar de parecerem imóveis as lapas são moluscos herbívoros que fazem deslocações sobre o substrato para rasparem pequenas algas das quais se alimentam. Para isso têm o auxílio de uma rádula na boca, que é uma espécie de fita com muitos dentes. Quando acabam estas excursões alimentares voltam exatamente ao mesmo local na rocha de onde saíram. Este comportamento é mais facilmente observável em marés-baixas noturnas, dias húmidos ou na maré cheia.",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    4,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_lapa_1"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_lapa_1"
                    )),
                    ""
            ),
            new EspecieAvencas(
                    "Burriés",
                    "Gibbula sp",
                    "Moluscos (Mollusca)",
                    "Género de pequenos caracóis marinhos.\n" +
                            "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, em forma de palmilha.\n" +
                            "Presentes no mediolitoral.",
                    "Herbívoros (algas e microalgas)",
                    "Tem uma \"tampa\" protetora (opérculo) que fecha, impedindo a perda de água.",
                    "",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    4,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_burries_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Búzio-negro",
                    "Melaraphe neritoides",
                    "Moluscos (Mollusca)",
                    "Género de pequenos caracóis marinhos.\n" +
                            "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, em forma de palmilha. Concha lisa mais alta que larga de cor cinzenta ou negra.\n" +
                            "Espécie característica do supralitoral, zona que apanha somente com salpicos de água.",
                    "Herbívoros (algas e microalgas)",
                    "Vive em fissuras das rochas, locais onde há concentração de humidade. Pode encontrar-se também em superfícies expostas.",
                    "",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    4,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_buzionegro_1",
                            "img_guiadecampo_buzionegro_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Polvo",
                    "Octopus vulgaris",
                    "Moluscos (Mollusca)",
                    "Corpo mole, sem qualquer revestimento externo, nem esqueleto interno. Possui oito braços, com ventosas dispostos em redor da boca.\n" +
                            "Como meios de defesa, o polvo possui a capacidade de largar tinta, de mudar a sua cor (camuflagem, através dos cromatóforos), e autotomia dos braços (pode largar um braço, em caso de perigo).\nEmbora esta espécie não seja típica da zona entre-marés, utiliza com frequência estas plataformas como abrigo, principalmente enquanto juvenil, pelo que pode ser observado na franja do infralitoral, ou por vezes nalgumas poças de maré. É uma espécie comercializada.",
                    "Carnívoros: de peixes, crustáceos e outros invertebrados.",
                    "",
                    "",
                    true,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    4,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_polvo_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Ouriço-do-mar",
                    "Paracentrotus lividus",
                    "Equinodermes (Echinodermata)",
                    "Corpo coberto de placas calcárias que formam geralmente um esqueleto rígido ou flexível, coberto por espinhos. Apresenta uma coloração arroxeada mas pode variar entre castanho-escuro, castanho-claro ou verde-oliva.\n" +
                            "Presentes na franja do infralitoral, formando agregados nas poças de maré.",
                    "Essencialmente herbívoros (de algas).",
                    "Os ouriços vão moldando a rocha, formando cavidades arredondadas, onde residem.",
                    "",
                    true,
                    false,
                    false,
                    true,
                    false,
                    true,
                    false,
                    3,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_ouricodomar_1",
                            "img_guiadecampo_ouricodomar_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Estrela-do-mar-de-espinhos",
                    "Marthasterias glacialis",
                    "Equinodermes (Echinodermata)",
                    "Corpo coberto de placas calcárias que formam geralmente um esqueleto rígido ou flexível, coberto muitas vezes por espinhos.\n" +
                            "Apresenta uma diversidade de tamanhos e cores, dependo do habitat em que se encontra. Presentes na franja do infralitoral.",
                    "Predador voraz, utiliza os espinhos para se alimentar. Carnívoros, alimentam-se de pequenos moluscos e crustáceos.",
                    "Redução da atividade. Prendem-se às rochas para não serem arrastados. Os espinhos também têm função de proteção.",
                    "",
                    true,
                    false,
                    false,
                    true,
                    false,
                    true,
                    false,
                    3,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_estreladomar_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Pepino-do-mar",
                    "Holothuria tubulosa",
                    "Equinodermes (Echinodermata)",
                    "Corpo cilíndrico, mas com a base achatada, com 3 fileiras de pés em forma de tubo. Corpo coberto por um muco.\nEmbora esta espécie não seja típica da zona entre-marés, é observada por vezes durante o período de maré-baixa, onde ficou retida até à próxima subida da maré.",
                    "Herbívora (algas, plâncton e pequenos detritos)",
                    "Redução da atividade; prendem-se às rochas para não serem arrastados",
                    "",
                    true,
                    true,
                    false,
                    false,
                    false,
                    true,
                    false,
                    3,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_pepinodomar_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Marachomba",
                    "Lipophrys pholis",
                    "Peixes ósseos (Chordata)",
                    "Peixes da família dos Blenídeos, de pequenas dimensões, residentes nas plataformas rochosas. Corpo alongado, achatado ventralmente, sem escamas.",
                    "Omnívoros (lapas, mexilhões, invertebrados e algas).",
                    "Ausência de escamas; corpo comprimido ventralmente; barbatanas com raios fortalecidos; olhos em posição elevada.",
                    "Estas espécies de peixes residentes na zona intertidal apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito imbricadas\n" +
                            "- Ausência bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a côr (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)\n",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    2,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_marachomba_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Peixe Índio",
                    "Coryphoblennius galerita",
                    "Peixes ósseos (Chordata)",
                    "Peixes da família dos Blenídeos, de pequenas dimensões, residentes nas plataformas rochosas. Corpo alongado, achatado ventralmente, sem escamas.\n" +
                            "Esta espécie distingue-se da anterior pela presença de uma crista na cabeça (maior no macho)",
                    "Omnívoros (lapas, mexilhões, invertebrados e algas).",
                    "Ausência de escamas; corpo comprimido ventralmente; barbatanas com raios fortalecidos; olhos em posição elevada.",
                    "Estas espécies de peixes residentes na zona intertidal apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito imbricadas\n" +
                            "- Ausência bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a côr (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)\n",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    2,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_peixeindio_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Caboz",
                    "Gobius cobitis",
                    "Peixes ósseos (Chordata)",
                    "Peixes da família dos gobídeos, de pequenas dimensões, residentes nas plataformas rochosas. Corpo alongado, achatado ventralmente. No caso destas espécies, o corpo é revestido por escamas, e a barbatana ventral está transformada numa ventosa.",
                    "Omnívoros (lapas, mexilhões, pequenos invertebrados e algas).",
                    "Escamas imbricadas; corpo comprimido ventralmente; barbatanas com raios fortalecidos; barbatanas ventrais unidas; olhos em posição elevada",
                    "Estas espécies de peixes residentes na zona intertidal apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito imbricadas\n" +
                            "- Ausência bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a côr (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)\n",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    2,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_caboz_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Algas Verdes (Clorofíceas)",
                    "",
                    "Algas",
                    "As algas são classificadas de acordo com a sua cor. A cor das algas resulta da presença de pigmentos diferentes. Esses pigmentos são responsáveis pela fotossíntese.",
                    "Estes organismos são produtores, isto é, produzem matéria orgânica através da fotossíntese, utilizando neste processo o dióxido de carbono, e libertando o oxigénio.",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_algasverdes_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "https://sapientia.ualg.pt/bitstream/10400.1/1643/2/Guia%20de%20Campo.pdf"
            ),
            new EspecieAvencas(
                    "Algas Vermelhas (Clorofíceas)",
                    "",
                    "Algas",
                    "As algas são classificadas de acordo com a sua cor. A cor das algas resulta da presença de pigmentos diferentes. Esses pigmentos são responsáveis pela fotossíntese.",
                    "Estes organismos são produtores, isto é, produzem matéria orgânica através da fotossíntese, utilizando neste processo o dióxido de carbono, e libertando o oxigénio.",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_algasvermelhas_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "https://sapientia.ualg.pt/bitstream/10400.1/1643/2/Guia%20de%20Campo.pdf"
            ),
            new EspecieAvencas(
                    "Algas Castanhas (Feofíceas)",
                    "",
                    "Algas",
                    "As algas são classificadas de acordo com a sua cor. A cor das algas resulta da presença de pigmentos diferentes. Esses pigmentos são responsáveis pela fotossíntese.",
                    "Estes organismos são produtores, isto é, produzem matéria orgânica através da fotossíntese, utilizando neste processo o dióxido de carbono, e libertando o oxigénio.",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_algascastanhas_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "https://sapientia.ualg.pt/bitstream/10400.1/1643/2/Guia%20de%20Campo.pdf"
            ),
            new EspecieAvencas(
                    "Alga calcária",
                    "Lithophyllum sp",
                    "Algas Calcárias",
                    "As algas calcárias, apesar de parecerem fazer parte da rocha, são algas impregnadas de carbonato de cálcio.\n" +
                            "Podem viver desde a zona entre-marés até profundidades 280 metros. É o tipo de alga capaz de viver a maiores profundidades.",
                    "",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_algacalcaria_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Verrucaria",
                    "Verrucaria maura",
                    "Líquenes",
                    "Características das plataformas rochosas da zona entre-marés, pertencem ao grupo dos líquenes, que são associações entre uma alga e um fungo (simbiose).\nÉ um líquene incrustrante, isto é, está completamente integrado na rocha.",
                    "",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_verrucaria_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Liquina",
                    "Lichina pygmaea",
                    "Líquenes",
                    "Características das plataformas rochosas da zona entre-marés, pertencem ao grupo dos líquenes, que são associações entre uma alga e um fungo (simbiose).\nA liquina é um líquen folhoso, isto é, tem uma estrutura semelhante a folhas.",
                    "",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    0,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_liquina_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Rola do mar",
                    "Arenaria interpres",
                    "Aves",
                    "Limícola pequena, robusta, de patas curtas e alaranjadas, peito e barriga brancos. Possui\n" +
                            "um babete preto bastante característico. Na Primavera passa a ostentar tonalidades laranja-amareladas no dorso, bastante características.",
                    "Alimenta-se de crustáceos e moluscos. Utilizam a plataforma rochosa durante a maré-baixa para se alimentar.",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    1,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_roladomar_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            ),
            new EspecieAvencas(
                    "Pilrito da areia",
                    "Calibris alba",
                    "Aves",
                    "Límicola de reduzidas dimensões, facilmente identificável pela sua cor clara. As regiões inferiores são brancas e as superiores cinzento-claro, onde se destacam as pequenas coberturas da asa pretas, formando uma mancha escura no ombro.",
                    "Alimenta-se de poliquetas, crustáceos e larvas de insetos. Utilizam a plataforma rochosa durante a maré-baixa para se alimentar.",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    1,
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_pilritodaareia_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    ""
            )
    );

    public static final List<EspecieRiaFormosa> dataEspeciesRiaFormosa = Arrays.asList(
            new EspecieRiaFormosa(
                    "Caranguejo-violinista",
                    "Afruca tangeri",
                    "Sapal",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "O caranguejo-violinista é assim chamado devido à sua pinça assimétrica e grande. Quando realiza movimentos com a pinça menor, para se alimentar, parece que está a tocar violino.\n" +
                            "Apresenta uma carapaça lisa ou com pequenos granulados, a coloração dos adultos pode variar entre violeta escuro, vermelho escuro, laranja e amarelo, com intensidades diferentes.\n" +
                            "Os juvenis são castanhos sendo facilmente confundidos com o lodo. \n" +
                            "Este caranguejo vive em galerias escavadas na areia ou no lodo.",
                    "Alimenta-se durante a maré baixa e a sua dieta centra-se em macroalgas, plantas de sapal e carcaças de animais.",
                    "Pinça menor para alimentação; presença de uma pinça maior nos machos que serve para a reprodução.",
                    "Esta espécie apresenta dimorfismo sexual o que significa que podemos distinguir o macho da fêmea. O macho diferencia-se da fêmea por apresentar uma das pinças maiores e mais desenvolvidas e é utilizada para a reprodução.",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_caranguejoviolinista_1",
                            "img_riaformosa_guiadecampo_caranguejoviolinista_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Pernilongo",
                    "Himantopus himantopus",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "Pernas muito compridas rosa-avermelhadas. Bico direito e fino como uma agulha. Apresenta uma plumagem preta e branca.\n" +
                            "Em voo mantém a cabeça e pernas esticadas. Os machos adultos distinguem-se pelos reflexos verdes na plumagem preta.\n" +
                            "Os pernilongos são pais extremosos e cuidam das crias até estas serem totalmente independentes.",
                    "Alimenta-se de insectos que encontra enquanto anda na água.",
                    "Bico comprido e direito para se alimentar de pequenos seres enterreados no lodo/areia, pernas compridas que lhe permitem andar no lodo, em espelhos de água; Plumagem que permite a camuflagem.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_pernilongo_1",
                            "img_riaformosa_guiadecampo_pernilongo_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Alfaiate",
                    "Recurvirostra avosetta",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "Padrão da plumagem preto e branco, bico preto estreito e curvado para cima. Pernas longas de cor azul pálido. O macho adulto distingue-se pelo bico maior, que parece menos curvado e pelas marcas pretas na cabeça.",
                    "Filtrador, alimenta-se de pequenos crustáceos, insectos e outros invertebrados que encontra ao filtrar, com o seu bico, a água e lama.",
                    "Bico comprido e encurvado, pernas compridas que lhe permitem andar no lodo, para se alimentar de pequenos seres enterrados no lodo/areia, através de filtragem.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_alfaiate_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Flamingo-comum",
                    "Phoenicopterus roseus",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "Apresenta coloração branca com tons rosados, coberturas vermelhas e penas de voo pretas. O bico é sobretudo cor-de-rosa, com a ponta preta. Patas rosas.\n" +
                            "Muitas vezes avistado em bandos. Em voo o pescoço mantem-se esticado.",
                    "Pequenos crustáceos, peixes e bivalves, procurando-os em zonas de pouca profundidade.",
                    "Não apresentam membrana interdigital; a forma dos bicos varia conforme os hábitos alimentares, podendo ser direito, encurvado ou em forma de espátula; patas compridas adaptadas a andar na água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_flamingocomum_1",
                            "img_riaformosa_guiadecampo_flamingocomum_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Colhereiro",
                    "Platalea leucorodia",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "Nidifica em arvores ou caniços e precisa de acesso a águas livres, pouco profundas e abrigadas,\n" +
                            "Bico longo em forma de espátula. Ao longe pode ser confundido com a Garça-branca. Penacho na nuca que faz lembrar um chefe índio. \n" +
                            "Em voo estica o pescoço.",
                    "Alimenta-se de moluscos, crustáceos e pequenos peixes.",
                    "Não apresentam membrana interdigital; a forma dos bicos varia conforme os hábitos alimentares, podendo ser direito, encurvado ou em forma de espátula; patas compridas adaptadas a andar na água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_colhereiro_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Garça-real",
                    "Ardea cinerea",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "De dimensão grande, robusta. Com coloração acinzentada nas partes superiores e branco-acinzentada nas partes inferiores. Mantem o pescoço retraído em voo, semelhando-se a um S. Bico direito com coloração amarelo-acinzentado e laranja na altura  da reprodução. Patas amarelas ou amarelo-acinzentadas.",
                    "Alimenta-se sobretudo de peixes.",
                    "Não apresentam membrana interdigital; a forma dos bicos varia conforme os hábitos alimentares, podendo ser direito, encurvado ou em forma de espátula; patas compridas adaptadas a andar na água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_garcareal_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Garça-branca-pequena",
                    "Egretta garzetta",
                    "Sapal",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "De dimensão média, mais pequena que a Garça-real. Silhueta esguia, totalmente branca e de patas negras, com dedos de cor amarelo-vivo. Bico preto. Na altura de reprodução, a nuca apresenta duas penas alongadas. Mantem o pescoço retraído, em voo, semelhando-se a um S.",
                    "Alimenta-se de peixes, rãs, insectos, caracóis.",
                    "Não apresentam membrana interdigital; a forma dos bicos varia conforme os hábitos alimentares, podendo ser direito, encurvado ou em forma de espátula; patas compridas adaptadas a andar na água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_garcabrancapequena_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Salgadeira",
                    "Atriplex halimus",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Arbusto halófito que pode atingir os 2,5m com numerosos ramos e folhas com 4 a 5cm branco-prateadas. Flores amareladas agrupadas.\n" +
                            "Planta característica do sapal baixo.\n" +
                            "A floração ocorre de julho a novembro.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_salgadeira_1",
                            "img_riaformosa_guiadecampo_salgadeira_2",
                            "img_riaformosa_guiadecampo_salgadeira_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Gramata-branca",
                    "Halimione portulacoides",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Arbusto que pode atingir até 1,5m. É constituído por numerosos ramos prostrados ou erectos. As folhas são opostas lanceoladas, assemelhando-se à ponta de uma lança, e espessas.\n" +
                            "Presente no sapal baixo, médio e alto.\n" +
                            "A floração ocorre de agosto a novembro.\n" +
                            "Espécie endémica da zona mediterrânica.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_gramatabranca_1",
                            "img_riaformosa_guiadecampo_gramatabranca_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    "Espécie endémica da zona Mediterrânica"
            ),
            new EspecieRiaFormosa(
                    "Suaeda",
                    "Suaeda maritima",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Planta anual com 10 a 15 cm de cor amarelo esverdeado. Folhas carnudas e suculentas e flor verdes. É uma planta erecta e com numerosas folhas alongadas. A sua floração ocorre de Junho a Setembro\n" +
                            "Presente no sapal baixo e próxima de habitats halófitos, sobretudo abaixo da linha da maré alta.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "Importante a nível ecológico uma vez que fixa a lama e ajuda na construção de habitats pantanosos.",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_suaeda_1",
                            "img_riaformosa_guiadecampo_suaeda_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Barrilha",
                    "Suaeda vera",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Planta muito ramificada até 1 metro, folhas carnudas de cor verdes ou avermelhadas.\n" +
                            "A floração ocorre de março a outubro.\n" +
                            "Pode ser identificada em sapais, estuários, em salinas e arribas litorais.\n" +
                            "Ocorre sobretudo em zonas de sapal médio, podendo estar presente também no sapal alto  ou nas arribas em zonas com muita presença de dejectos de aves marinhas.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_barrilha_1",
                            "img_riaformosa_guiadecampo_barrilha_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Espargo-do-mar",
                    "Salicornia nitens",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Também conhecida por Salicornia ramosissima, é uma planta anual, com caules até 30cm e por norma bastante ramificada, erectos e com aspecto carnudo. A sua inflorescência apresenta-se em forma de espiga.\n" +
                            "Presente no sapal baixo e médio, em solos salinos temporariamente encharcados, o que significa que está sujeito às marés.\n" +
                            "A sua floração ocorre de maio a novembro.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "Por ser uma planta rica em sais minerais e proteínas e com um sabor salgado, tem sido utilizada na alimentação.",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_espargodomar_1",
                            "img_riaformosa_guiadecampo_espargodomar_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Salicórnia",
                    "Salicornia fruticosa",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Planta halófita de crescimento lento. As folhas apresentam-se verdes na primavera e no verão e ficam avermelhadas durante o outono. As folhas têm forma de escama. \n" +
                            "Presente no sapal médio, podendo ser identificada também nas margens de canais e taludes.\n" +
                            "A sua floração ocorre de agosto a outubro.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_salicornia_1",
                            "img_riaformosa_guiadecampo_salicornia_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Sarcocórnia",
                    "Sarcocornia perennis",
                    "Sapal",
                    "Flora",
                    "Amaranthaceae",
                    "Planta perene ramificada, com ramos até 70cm de altura. As folhas são reduzidas a uma escama aguda.\n" +
                            "Encontra-se no sapal baixo, nas margens de canais, na orla de prados de morraça ou de matos halófilos.\n" +
                            "Época de floração de agosto a novembro.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "Esta planta é rica em minerais, ácidos gordos, proteínas, vitaminas e fibras podendo ser um bom substituto do sal na nossa alimentação.",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_sarcocornia_1",
                            "img_riaformosa_guiadecampo_sarcocornia_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Abrótomo",
                    "Artemisia campestris",
                    "Sapal",
                    "Flora",
                    "Asteraceae",
                    "Sub-arbusto ramoso, sem pelos podendo ser aromático. Caules com 30 a 80 cm de altura, estriados e erectos, apresentando uma cor vermelho-acastanhada. As folhas são carnudas, flor amarela. Os frutos apresentam uma cor acastanhada.\n" +
                            "A floração ocorre de setembro a outubro.\n" +
                            "Presente no sapal alto.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "É importante pelo seu papel no equilíbrio de ecossistemas uma vez que serve de alimento a variadíssimas larvas de borboletas (Lepidoptera) e algumas destas larvas alimentam-se exclusivamente desta planta. Algumas zonas, nesta espécie, pode aparecer estruturas avermelhadas, sendo um crescimento anormal de tecido por picadas de insectos.",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_abrotomo_1",
                            "img_riaformosa_guiadecampo_abrotomo_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_abrotomo_3"
                    )),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Juncos",
                    "Juncus sp",
                    "Sapal",
                    "Flora",
                    "Juncaceae",
                    "O género Juncus é um grupo de plantas semelhantes às gramíneas e crescem em zonas alagadiças. Esta planta apresenta caules cilíndricos, com 3 fileiras de folhas. \n" +
                            "Flores pequenas esverdeadas ou castanhas.\n" +
                            "O junco é uma planta verde-escura e flexível. Pode atingir um 1,5 metro de altura.\n" +
                            "Presente no sapal alto.",
                    "",
                    "Folhas  mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "Este género é utilizado como fonte de alimento por algumas larvas da ordem Lepidoptera.",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_juncos_1",
                            "img_riaformosa_guiadecampo_juncos_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Cistanche",
                    "Cistanche phelypaea",
                    "Sapal",
                    "Flora",
                    "Orobanchaceae",
                    "Planta com tamanho entre 20 a 50cm.\n" +
                            "Folhas pequenas e estreitas e normalmente de cor castanha.\n" +
                            "Presente no sapal alto.\n" +
                            "A floração ocorre entre março e junho.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "Esta planta pode parasitar muitas espécies da família Amaranthaceae como Sueda vera, Atriplex halimus, sendo uma espécie parasita que não realiza a fotossíntese. Possuem estruturas de sucção que lhes permite obter a seiva elaborada (haustórios).",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_cistanche_1",
                            "img_riaformosa_guiadecampo_cistanche_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Limonium",
                    "Limonium algarvense",
                    "Sapal",
                    "Flora",
                    "Plumbaginaceae",
                    "Plantas herbáceas perenes ou pequenos arbustos. Folhas sempre presentes, geralmente com a margem inteira às vezes serrilhada. A sua floração ocorre de maio a agosto.\n" +
                            "Espécie endémica do Sul da Península ibérica e Marrocos e com estatuto de conservação segundo a IUCN de “Quase ameaçada”\n" +
                            "Presente no sapal baixo.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_limonium_1",
                            "img_riaformosa_guiadecampo_limonium_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    "Espécie endémica do Sul da Península ibérica e Marrocos"
            ),
            new EspecieRiaFormosa(
                    "Limoniastrum",
                    "Limoniastrum monopetalum",
                    "Sapal",
                    "Flora",
                    "Plumbaginaceae",
                    "Arbusto de tons verde, com folhas lanceoladas, associando à ponta de uma lança e flores arroxeadas. Presente no sapal alto, podendo também ser encontrada em terrenos incultos e areias marítimas. Raramente, encontra-se em locais pedregosas do litoral.\n" +
                            "A floração ocorre entre março e novembro.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_limoniastrum_1",
                            "img_riaformosa_guiadecampo_limoniastrum_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Morraça",
                    "Spartina maritima",
                    "Sapal",
                    "Flora",
                    "Poaceae",
                    "Planta vivaz o que significa que vive mais do que dois anos. Aplica-se sobretudo a plantas onde a parte aérea (folhas) é herbácea e renova-se anualmente.\n" +
                            "Cada espigueta possui 1 ou, mais raramente, 2 flores, esverdeadas tornando-se amareladas quando maduras e mede entre 5 a 15cm\n" +
                            "A sua época de floração é de julho a setembro\n" +
                            "Presente no sapal baixo.",
                    "",
                    "Folhas mais espessas ou suculentas e caules suculentos, para armazenar água; área foliar reduzida e cutícula de revestimento para evitar perda de água; maior massa radicular para maior absorção; pelos secretores de sal.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_morraca_1",
                            "img_riaformosa_guiadecampo_morraca_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Tomate-do-mar",
                    "Actinia equina",
                    "Intertidal arenoso",
                    "Fauna",
                    "Cnidaria",
                    "Corpo dividido em 3 partes: tentáculos, coluna e base, onde se encontra o pé da base que se fixa à superfície duras como rocha, pedras ou outras superfícies.\n" +
                            "Ocorre no intertidal, sobretudo no mediolitoral. Estas espécies também podem viver em profundidades até 20m.\n" +
                            "O pé pode ter até 5cm de diâmetro, coluna lisa e pode apresentar uma coloração verde, vermelha ou castanha.",
                    "Alimenta-se de diversos organismos como moluscos, bivalves, insectos, caracóis e lesmas do mar, entre outros.",
                    "Consegue sobreviver fora de água e nestas condições recolhe os seus tentáculos para dentro do corpo, evitando a perda de água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_tomatedomar_1",
                            "img_guiadecampo_tomatedomar_2",
                            "img_guiadecampo_tomatedomar_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Anémona",
                    "Anemonia sulcata",
                    "Intertidal arenoso",
                    "Fauna",
                    "Cnidaria",
                    "Apresenta grandes tentáculos com uma coloração cinzento-acastanhados ou verde-vivo. As anémonas que têm a tonalidade verde apresentam, nas pontas dos tentáculos, uma cor purpura. A coluna tem uma coloração avermelhada ou cinzento-acastanhada.\n" +
                            "Presentes no intertidal, sobretudo no infralitoral e também em poças de maré.",
                    "Sendo carnívoras alimentam-se de juvenis de peixes, crustáceos, plâncton.",
                    "Ao contrário do tomate-do-mar, as anémonas não retraem os tentáculos para o interior do seu corpo, estando mais expostas à secura quando se encontram na maré baixa, nesta situação, as anémonas reduzem a superfície corporal exposta.",
                    "Estes organismos usam os tentáculos para capturar camarões, juvenis de peixe e outros pequenos animais, dos quais se alimentam.\n" +
                            "Os tentáculos são armados com muitos cnidócitos, que são células de defesa e também um meio de capturar presas. Os cnidócitos são uma espécie de \"chicotes“, também chamados de nematocistos, que contêm uma pequena vesícula cheia de veneno e um sensor; quando o sensor é tocado, ativa o \"chicote\" que crava na presa e injeta o veneno. O veneno serve para paralisar a presa. O veneno é altamente tóxico para os pexes e crustáceos, que são a presa natural das anémonas.\n" +
                            "Atenção que esta substância pode causar alergia na nossa pele, especialmente no caso das anémonas.",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_anemona_1",
                            "img_guiadecampo_anemona_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Camarão-branco-legítimo",
                    "Palaemon serratus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Dentre as principais características, dos crustáceos salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen (mas há exceções). Os camarões possuem o corpo protegido por uma cutícula calcificada. Coloração acinzentada a rosa-pálido com estrias escuras. \n" +
                            "Está presente no mediolitoral, no entanto, também pode ser encontrada até aos 50m de profundidade. Espécie de actividade nocturna, pode ser encontrado nas poças de maré.",
                    "Espécie omnívora, alimenta-se de algas, matéria orgânica em decomposição, pequenos crustáceos, moluscos e outros pequenos invertebrados. Por vezes também são canibais (comem a sua própria espécie).",
                    "Toleram grandes variações de salinidade e temperatura; reduzem a atividade; fazem osmorregulação (equilíbrio entre a quantidade de água e sais minerais no corpo); permanecendo em aglomerados de algas, debaixo de pedras ou fendas.",
                    "Tanto no caso do camarão como do caranguejo, as fêmeas transportam os ovos  no seu próprio corpo. No caso dos camarões, as fêmeas transportam-nos durante 2 a 4 meses, e como o corpo é transparente, podemos observar os ovos a olho nú. \n" +
                            "No caso dos caranguejos, os ovos são transportados pela fêmea, presos às sedas dos pleópodes (apêndices transformados, que se encontram na parte dobrada do abdómen). Durante esse tempo a fêmea limpa e areja a massa de ovos. Nesta espécie, consegue-se distinguir o sexo do animal através da forma do abdómen: triangular nos machos (imagem de cima) versus arredondado nas fêmeas (imagem de baixo).",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_camarao_1"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_caranguejo_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Caranguejo-verde",
                    "Carcinus maenas",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Dentre as principais características dos crustáceos, salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen. Os caranguejos possuem uma carapaça calcária, que protege o corpo. Caranguejo de pequeno tamanho com uma carapaça serrilhada, apresentando 5 dentes, no bordo, em cada lado. Apresenta uma coloração esverdeada a avermelhada.\n" +
                            "Está presente no mediolitoral. Pode estar presente no infralitoral até uma profundidade máxima de 200m.",
                    "Espécie omnívora, pode alimentar-se de plantas, moluscos, algas, artrópodes e anelídeos.",
                    "Toleram grandes variações de salinidade e temperatura; reduzem a atividade, permanecendo em aglomerados de algas, debaixo de pedras ou fendas.",
                    "Tanto no caso do camarão como do caranguejo, as fêmeas transportam os ovos  no seu próprio corpo. No caso dos camarões, as fêmeas transportam-nos durante 2 a 4 meses, e como o corpo é transparente, podemos observar os ovos a olho nú. \n" +
                            "No caso dos caranguejos, os ovos são transportados pela fêmea, presos às sedas dos pleópodes (apêndices transformados, que se encontram na parte dobrada do abdómen). Durante esse tempo a fêmea limpa e areja a massa de ovos. Nesta espécie, consegue-se distinguir o sexo do animal através da forma do abdómen: triangular nos machos (imagem de cima) versus arredondado nas fêmeas (imagem de baixo).",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_caranguejoverde_1",
                            "img_riaformosa_guiadecampo_caranguejoverde_2"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_caranguejo_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Caranguejo-preto",
                    "Pachygrapsus marmoratus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Dentre as principais características dos crustáceos, salientam-se  dois pares de antenas, e o corpo dividido em cefalotórax e abdómen. Os caranguejos possuem uma carapaça calcária, que protege o corpo. Pequeno caranguejo com uma carapaça quadrada de 22 a 36 mm de comprimento. Apresenta uma coloração violeta-escuro com um marmoreado amarelado. Apresenta 3 dentes em cada lado da carapaça.\n" +
                            "O polvo é o predador desta espécie.\n" +
                            "Presente no mediolitoral.",
                    "Omnívoro, alimenta-se de algas e vários animais como mexilhão e lapas.",
                    "Toleram grandes variações de salinidade e temperatura; reduzem a atividade, permanecendo em aglomerados de algas, debaixo de pedras ou fendas.",
                    "Tanto no caso do camarão como do caranguejo, as fêmeas transportam os ovos  no seu próprio corpo. No caso dos camarões, as fêmeas transportam-nos durante 2 a 4 meses, e como o corpo é transparente, podemos observar os ovos a olho nú. \n" +
                            "No caso dos caranguejos, os ovos são transportados pela fêmea, presos às sedas dos pleópodes (apêndices transformados, que se encontram na parte dobrada do abdómen). Durante esse tempo a fêmea limpa e areja a massa de ovos. Nesta espécie, consegue-se distinguir o sexo do animal através da forma do abdómen: triangular nos machos (imagem de cima) versus arredondado nas fêmeas (imagem de baixo).",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_caranguejo_1",
                            "img_guiadecampo_caranguejo_2"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_caranguejo_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Caranguejo-eremita",
                    "Pagurus bernhardus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Podem atingir um comprimento máximo de 8cm. Corpo divido em dois segmentos: cefalotórax e abdómen. Apresenta uma coloração avermelhada ou castanha.  As larvas desta espécie podem ser encontradas em poças sob rochas e algas marinhas.\n" +
                            "Está presente no mediolitoral.",
                    "Necrófagos, alimentam-se de bivalves, restos de animais mortos. Quando o alimento escasseia podem ser canibais (alimentam-se da própria espécie).",
                    "Ocupam conchas abandonadas para protecção dos seus corpos moles. Os 2 últimos pares de pernas são mais reduzidas e servem para fixar a concha. O tamanho da concha é muito importante. Uma concha muito grande não lhe confere protecção, no entanto, uma concha muito pequena restringe o seu crescimento.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_caranguejoeremita_1",
                            "img_riaformosa_guiadecampo_caranguejoeremita_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Cracas pequenas",
                    "Chthamalus spp",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                            "Presentes no mediolitoral.",
                    "Filtradores (alimentam-se de partículas em suspensão na água)",
                    "Fecham-se no interior das placas.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cracaspequenas_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Cracas grandes",
                    "Balanus sp",
                    "Intertidal arenoso",
                    "Fauna",
                    "Crustáceos (Arthropoda)",
                    "Os crustáceos apresentam grande diversidade morfológica. No caso destas espécies, em vez de uma única carapaça, o corpo está protegido por placas calcárias, que protege parte ou todo o corpo.\n" +
                            "Presentes no mediolitoral.",
                    "Filtradores (alimentam-se de partículas em suspensão na água)",
                    "Fecham-se no interior das placas.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cracasgrandes_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Lapa",
                    "Patella spp",
                    "Intertidal arenoso",
                    "Fauna",
                    "Moluscos (Mollusca)",
                    "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, com a forma de disco." +
                            "Presente no mediolitoral.",
                    "Herbívoro (raspa as algas).",
                    "Fixam-se firmemente às rochas, e escavam uma pequena cavidade, à qual se ajusta perfeitamente, impedindo a perda de água.",
                    "Apesar de parecerem imóveis as lapas são moluscos herbívoros que fazem deslocações sobre o substrato para rasparem pequenas algas das quais se alimentam. Para isso têm o auxílio de uma rádula na boca, que é uma espécie de fita com muitos dentes. Quando acabam estas excursões alimentares voltam exatamente ao mesmo local na rocha de onde saíram. Este comportamento é mais facilmente observável em marés-baixas noturnas, dias húmidos ou na maré cheia.",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_lapa_1"
                    )),
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_sabiasque_lapa_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Mexilhão",
                    "Mytilus galloprovincialis",
                    "Intertidal arenoso",
                    "Fauna",
                    "Moluscos (Mollusca)",
                    "Corpo mole mas protegido por uma concha, bivalve.\n" +
                            "Presente no mediolitoral e no infralitoral.",
                    "Filtradores (de pequenas partículas orgânicas flutuantes, plâncton, e pequenas algas - fitoplâncton).",
                    "As duas valvas protetoras fecham-se para armazenar água. Fixam-se firmemente às rochas, através de fibras chamadas bissos.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_mexilhao_1",
                            "img_guiadecampo_mexilhao_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Burriés",
                    "Gibbula sp",
                    "Intertidal arenoso",
                    "Fauna",
                    "Moluscos (Mollusca)",
                    "Género de pequenos caracóis marinhos. \n" +
                            "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, em forma de palmilha.\n" +
                            "Presentes no mediolitoral.",
                    "Herbívoros (algas e microalgas)",
                    "Tem uma “tampa” protetora (opérculo) que fecha, impedindo a perda de água.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_burries_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Búzio-negro",
                    "Melaraphe neritoides",
                    "Intertidal arenoso",
                    "Fauna",
                    "Moluscos (Mollusca)",
                    "Género de pequenos caracóis marinhos. \n" +
                            "Corpo mole mas protegido por uma concha, de formas variadas. Pé característico, em forma de palmilha. Concha lisa mais alta que larga de cor cinzenta ou negra. \n" +
                            "Espécie característica do supralitoral, zona que apanha somente com salpicos de água.",
                    "Herbívoros (algas e microalgas)",
                    "Vive em fissuras das rochas, locais onde há concentração de humidade. Pode encontrar-se também em superfícies expostas.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_buzionegro_1",
                            "img_guiadecampo_buzionegro_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Polvo",
                    "Octopus vulgaris",
                    "Intertidal arenoso",
                    "Fauna",
                    "Moluscos (Mollusca)",
                    "Corpo mole, sem qualquer revestimento externo, nem esqueleto interno. Possui oito braços, com ventosas dispostos em redor da boca.\n" +
                            "Pode ser encontrado na franja do infralitoral.\n" +
                            "Embora esta espécie não seja típica da zona entre-marés, utiliza com frequência estas plataformas como abrigo, principalmente enquanto juvenil, pelo que pode ser observado na franja do infralitoral, ou por vezes nalgumas poças de maré. É uma espécie comercializada.",
                    "Carnívoro, alimenta-se de peixes, crustáceos e outros invertebrados.",
                    "Como meios de defesa, o polvo possui a capacidade de largar tinta, de mudar a sua cor (camuflagem, através dos cromatóforos), e autotomia dos braços (pode largar um braço, em caso de perigo). Como não ossos consegue esconder-se em pequenos espaços.\n" +
                            "As ventosas servem para se fixar nas rochas.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_polvo_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Ouriço-do-mar",
                    "Paracentrotus lividus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Equinodermes (Echinodermata)",
                    "Corpo coberto de placas calcárias que formam geralmente um esqueleto rígido ou flexível, coberto por espinhos. Apresenta uma coloração arroxeada mas pode variar entre castanho-escuro, castanho-claro ou verde-oliva. \n" +
                            "Presentes na franja do infralitoral, formando agregados nas poças de maré.",
                    "Essencialmente herbívoros (de algas).",
                    "Os ouriços vão moldando a rocha, formando cavidades arredondadas, onde residem.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_ouricodomar_1",
                            "img_guiadecampo_ouricodomar_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Estrela vermelha de pente",
                    "Astropecten aranciacus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Equinodermes (Echinodermata)",
                    "Corpo coberto de placas calcárias que formam geralmente um esqueleto rígido ou flexível, coberto muitas vezes por espinhos.\n" +
                            "A cor vai desde vermelho vivo a acastanhado. Presentes na franja do infralitoral.",
                    "Predador voraz, utiliza os espinhos para se alimentar. Carnívoros, alimentam-se de pequenos moluscos e crustáceos.",
                    "Redução da atividade. Prendem-se às rochas para não serem arrastados. Os espinhos também têm função de proteção.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_estrelavermelhadepente_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Pepino-do-mar",
                    "Holothuria arguinensis",
                    "Intertidal arenoso",
                    "Fauna",
                    "Equinodermes (Echinodermata)",
                    "Corpo cilíndrico, mas com a base achatada, com 3 fileiras de pés em forma de tubo. Corpo coberto por um muco. \n" +
                            "Embora esta espécie não seja típica da zona entre-marés, é observada por vezes durante o período de maré-baixa, onde ficou retida até à próxima subida da maré.\n" +
                            "Pode ser encontrada na franja do infralitoral.",
                    "Herbívora (algas, plâncton e pequenos detritos)",
                    "Redução da atividade; prendem-se às rochas para não serem arrastados; desenvolvimento de toxinas para proteção contra predadores.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_pepinodomar_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Ofiúro-serpente",
                    "Ophioderma longicauda",
                    "Intertidal arenoso",
                    "Fauna",
                    "Equinodermes (Echinodermata)",
                    "Coberto de pequenos espinhos é formado por 5 braços finos que se unem no disco central.\n" +
                            "Podem apresentar acastanhadas a vermelho alaranjado.\n" +
                            "São sensíveis à luz e preferem zonas abrigadas, como fendas ou rochas.\n" +
                    "A acidificação dos oceanos, provocada pelas alterações climáticas, podem resultar na redução na capacidade de regeneração dos ofiúros tendo um impacto negativo sobre estes animais.",
                    "Alimentam-se, durante a noite, de pequenos moluscos.",
                    "Têm a capacidade de se regenerarem rapidamente. Pensa-se que para fugirem aos predadores, os ofiúros libertam um dos seus braços.",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_ofiuroserpente_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Ascídia",
                    "Ascidia mentula",
                    "Intertidal arenoso",
                    "Fauna",
                    "Ascídia (Chordata)",
                    "Aparentam ser pouco complexas mas na verdade estão muito próximas dos vertebrados. \n" +
                            "Sésseis na fase adulta (estão fixos no substrato) e o seu movimento deve-se à contracção do corpo através das fibras musculares. São solitárias ou formam colónias. Apresentam diversas formas e cores\n" +
                            "Ascidia mentula é uma espécie solitária e apresenta um corpo translúcido, por vezes esbranquiçado, rosa ou acastanhado. A altura do corpo varia entre 5 a 18cm. Presente na franja do infralitoral.",
                    "A. mentula alimenta-se por filtração, de plâncton.\n" +
                            "Algumas espécies são consideradas carnívoras.",
                    "Corpo coberto por uma túnica (secreção da tunicina) que lhes confere protecção.",
                    "As ascídias têm uma grande importância ecológica uma vez que são animais com uma grande capacidade de filtração, desempenhando um papel muito importante na purificação da água. \n" +
                            "Em algumas partes do mundo, como por exemplo Japão ou França, podem fazer parte da alimentação humana. Também têm um papel importante na saúde humana podendo ser usadas para o combate de muitas doenças.",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_ascidia_1",
                            "img_riaformosa_guiadecampo_ascidia_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Blénio tentaculado",
                    "Parablennius tentacularis",
                    "Intertidal arenoso",
                    "Fauna",
                    "Peixes ósseos (Chordata)",
                    "Peixes de pequenas dimensões, residentes nas plataformas rochosas. Corpo alongado, achatado ventralmente, atingindo os 15cm de comprimento.\n" +
                            "No caso destas espécies, da família dos bleniídeos, o corpo é muito flexível e sem escamas.  Presença de uma crista na cabeça.",
                    "Omnívoros (lapas, mexilhões, invertebrados e algas).",
                    "Ausência de escamas, corpo comprimido ventralmente, barbatanas com raios fortalecidos, olhos em posição elevada",
                    "Estas espécies de peixes apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito sobrepostas\n" +
                            "- Ausência de bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a cor (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_bleniotentaculado_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Marachomba-pavão",
                    "Salaria pavo",
                    "Intertidal arenoso",
                    "Fauna",
                    "Peixes ósseos (Chordata)",
                    "Corpo alongado, comprimido nas laterais e com cabeça e testa alta. Tem um tentáculo curto por cima de cada olho, no entanto é quase invisível. Barbatanas arredondadas e a dorsal longa. Cor verde-amarelado com faixas mais escuras, linhas verticais e pontos azuis electricos pelo corpo todo. No caso destas espécies, da família dos bleniídeos, o corpo é muito flexível e sem escamas.",
                    "Omnívoros (lapas, mexilhões, invertebrados e algas).",
                    "Ausência de escamas, corpo comprimido ventralmente, barbatanas com raios fortalecidos,  olhos em posição elevada",
                    "Estas espécies de peixes apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito sobrepostas\n" +
                            "- Ausência de bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a cor (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_marachombapavao_1",
                            "img_riaformosa_guiadecampo_marachombapavao_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Caboz",
                    "Gobius paganellus",
                    "Intertidal arenoso",
                    "Fauna",
                    "Peixes ósseos (Chordata)",
                    "Corpo alongado, cilíndrico, com cabeça larga e os olhos na parte superior. \n" +
                            "No caso destas espécies, da família dos gobiídeos, o corpo é revestido por escamas, e a barbatana ventral está transformada numa ventosa. Tem uma cor acastanhada com manchas mais claras.",
                    "Omnívoros (lapas, mexilhões, pequenos invertebrados e algas).",
                    "Escamas imbricadas; corpo comprimido ventralmente; barbatanas com raios fortalecidos; barbatanas ventrais unidas, formando uma ventosa; olhos em posição elevada.",
                    "Estas espécies de peixes apresentam um conjunto de adaptações  que lhes permite sobreviver nestas plataformas rochosas, enfrentando não só os períodos de emersão provocados pela maré-baixa, como  o perigo de serem arrastados pelas turbulência das ondas:\n" +
                            "- Corpo de pequenas dimensões e comprimido ventralmente\n" +
                            "- Ausência de escamas (presença de um muco que facilita as trocas gasosas) ou escamas muito sobrepostas\n" +
                            "- Ausência de bexiga natatória (são peixes que vivem associados ao fundo)\n" +
                            "- Barbatanas peitorais muito fortes (são capazes de se deslocar sobre o substrato)\n" +
                            "- Grande mobilidade da cabeça e olhos em posição elevada (permite detetar predadores)\n" +
                            "- Capacidade de alterar a cor (mimetismo)\n" +
                            "- Capacidade de memorizar itinerários e a localização de abrigos (muitas espécies voltam sempre às mesmas poças nos períodos de maré-baixa)",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_riaformosa_guiadecampo_caboz_1",
                            "img_riaformosa_guiadecampo_caboz_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Algas",
                    "",
                    "Intertidal arenoso",
                    "Flora",
                    "Algas",
                    "As algas são classificadas de acordo com a sua cor. A cor das algas resulta da presença de pigmentos diferentes. Esses pigmentos são responsáveis pela fotossíntese.",
                    "Estes organismos são produtores, isto é, produzem matéria orgânica através da fotossíntese, utilizando neste processo o dióxido de carbono, e libertando o oxigénio.",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_algasverdes_1",
                            "img_guiadecampo_algasvermelhas_1",
                            "img_guiadecampo_algascastanhas_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "https://sapientia.ualg.pt/bitstream/10400.1/1643/2/Guia%20de%20Campo.pdf",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Pilrito da areia",
                    "Calibris alba",
                    "Intertidal arenoso",
                    "Fauna",
                    "Aves Limícolas (Chordata)",
                    "Límicola de reduzidas dimensões, facilmente identificável pela sua cor clara. As regiões inferiores são brancas e as superiores cinzento-claro, onde se destacam as pequenas coberturas da asa pretas, formando uma mancha escura no ombro.",
                    "Alimenta-se de poliquetas, crustáceos e larvas de insetos. Utilizam a plataforma rochosa durante a maré-baixa para se alimentar.",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_pilritodaareia_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Narciso-das-areias",
                    "Pancratium maritimum",
                    "Dunas",
                    "Flora",
                    "Amaryllidaceae",
                    "Planta vivaz, herbácea, pode atingir os 50 cm de altura e apresenta uma cor cinzento-azulada. Planta bolbosa.\n" +
                            "As folhas são espessas e planas e as flores brancas e aromáticas.\n" +
                            "Pode ser encontrada em dunas e areais costeiros, principalmente na duna primária, em pleno sol, suportando períodos prolongados de seca\n" +
                            "Época de floração ocorre de maio a setembro.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_narcisodasareias_1",
                            "img_guiadecampo_narcisodasareias_2",
                            "img_guiadecampo_narcisodasareias_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Cardo-marítimo",
                    "Eryngium maritimum",
                    "Dunas",
                    "Flora",
                    "Apiaceae",
                    "Planta vivaz, herbácea e robusta. Os caules são verticais, têm entre 15 a 60cm de altura e apresentam uma tonalidade branca ou cinzento-azulada. As folhas são rígidas e um pouco espessas. As flores têm uma cor azulada.\n" +
                            "Presente no litoral, em zonas de areias, principalmente nas dunas secundárias.\n" +
                            "Época de floração de maio a setembro.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cardomaritimo_1",
                            "img_guiadecampo_cardomaritimo_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Pseudorlaia",
                    "Pseudorlaya pumila",
                    "Dunas",
                    "Flora",
                    "Apiaceae",
                    "É uma planta anual (terófita), pequena. Caules numerosos  e ramificados. Flores brancas, rosas ou roxas. Folhas com pêlos, dentadas e divididas (em que os segmentos formados alcançam o nervo central). \n" +
                            "Presente no litoral, principalmente na zona interdunar. Pode estar também presente nas dunas primárias.\n" +
                            "A época de floração ocorre de março a junho.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_pseudorlaia_1",
                            "img_guiadecampo_pseudorlaia_2",
                            "img_guiadecampo_pseudorlaia_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Erva-do-caril",
                    "Helichrysum italicum",
                    "Dunas",
                    "Flora",
                    "Asteraceae",
                    "Sub-arbusto aromático com cerca de 10 a 35cm de altura. Folhas estreitas e verde-prateadas com bordo enrolado. \n" +
                            "A época de floração ocorre entre maio a setembro e encontra-se em na zona interdunar.\n" +
                            "Também podem ser identificadas sob coberto de pinhais.",
                    "",
                    "",
                    "",
                    "O género Helichrysum deriva do grego Helios que significa sol e Chrysos que significa ouro, sendo uma analogia para com as inflorescências amarelas e por estas espécies gostarem bastante de luz.\n" +
                            "Ao esmagar uma flor, na mão, identifica-se o cheiro a caril, daí o nome comum dado a esta planta.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_ervadocaril_1",
                            "img_guiadecampo_ervadocaril_2",
                            "img_guiadecampo_ervadocaril_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Carqueja-mansa",
                    "Cakile maritima",
                    "Dunas",
                    "Flora",
                    "Brassicaceae",
                    "Planta anual e halófita, forma pequenas moitas que podem atingir os 45cm de altura. Folhas carnudas e brilhantes devido à presença de uma cutícula protectora.\n" +
                            "As flores são brancas ou um pouco rosadas dispostas em pequenos cachos.\n" +
                            "Pode ser encontrada em praias, principalmente em dunas embrionárias.\n" +
                            "Por vezes pode ser identificada nas bermas de caminhos.\n" +
                            "Época de floração de março a dezembro.",
                    "",
                    "",
                    "",
                    "Cakile maritima é uma planta da família das brassicáceas sendo a mesma família onde podemos encontrar também as couves e os nabos.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_carquejamansa_1",
                            "img_guiadecampo_carquejamansa_2",
                            "img_guiadecampo_carquejamansa_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Corregola",
                    "Corrigiola littoralis",
                    "Dunas",
                    "Flora",
                    "Caryophyllaceae",
                    "Planta anual, com caules inclinados sobre o solo, delgados e muito ramificados desde a base, pode atingir os 30cm de altura. As folhas apresentam a margem inteira e um pouco ondulada. As flores apresentam uma cor branca.\n" +
                            "Época de floração todo o ano \n" +
                            "Presente na duna secundária.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_corregola_1",
                            "img_guiadecampo_corregola_2",
                            "img_guiadecampo_corregola_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Erva-dos-linheiros",
                    "Paronychia argentea",
                    "Dunas",
                    "Flora",
                    "Caryophyllaceae",
                    "Planta vivaz, herbácea com caules muito ramosos, com 5 a 40cm. Apresenta muitos entrenós podendo mesmo exceder as folhas. Caules com um tom rosado e flores esbranquiçadas. Folhas opostas e lanceoladas (recordando a ponta de uma lança)\n" +
                            "Presente na zona interdunar. Também pode estar presente em margens de caminhos e dos campos.\n" +
                            "Época de floração de junho a setembro.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_ervadoslinheiros_1",
                            "img_guiadecampo_ervadoslinheiros_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Barrilheira",
                    "Salsola kali",
                    "Dunas",
                    "Flora",
                    "Chenopodiaceae",
                    "Planta anual (terófita), herbácea, folha com presença de pelos, caule verde e com pelos. Flores cor-de-rosa.\n" +
                            "Presente na duna embrionária. Também pode ser encontradas em solos arenosos salgadiços e ricos em matéria orgânica, perto do litoral. \n" +
                            "Época de floração de maio a novembro",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_barrilheira_1",
                            "img_guiadecampo_barrilheira_2",
                            "img_guiadecampo_barrilheira_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Cordeirinhos-da-praia",
                    "Otanthus maritimus",
                    "Dunas",
                    "Flora",
                    "Compositae",
                    "Subarbusto, lenhoso na base com um aspecto esbranquiçado. Os caules podem atingir até 50cm\n" +
                            "As folhas são carnudas e podem ser inteiras ou com recortes arredondados muito pequenos.\n" +
                            "As flores são amarelas e em forma de tubo.\n" +
                            "Presente em todo o litoral, principalmente nas dunas primárias, podendo ser encontrado também nas dunas embrionárias.\n" +
                            "A época de floração ocorre de maio a outubro.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_cordeirinhosdapraia_1",
                            "img_guiadecampo_cordeirinhosdapraia_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Couve-marinha",
                    "Calystegia soldanella",
                    "Dunas",
                    "Flora",
                    "Convolvulaceae",
                    "Planta vivaz, rastejante que pode atingir os 50cm. As folhas são mais ou menos carnudas. As flores apresentam tons rosados.\n" +
                            "A época de floração ocorre de abril a julho e está presente nas praias e dunas costeiras, preferencialmente primárias, podendo estar presente também nas dunas embrionárias.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_couvemarinha_1",
                            "img_guiadecampo_couvemarinha_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Arruda-da-praia",
                    "Pycnocomon rutifolium",
                    "Dunas",
                    "Flora",
                    "Dipsacaceae",
                    "Planta herbácea, perene e ramosa.\n" +
                            "Os multicaules podem atingir os 105cm de altura. Folhas dentadas e divididas (em que os segmentos formados alcançam o nervo central). As flores apresentam uma cor branca.\n" +
                            "Presente na duna secundárias, na zona limite para o sub-bosque/mata, sobre pinhas e outros matos dunares. Presente também nas bermas de caminhos e em areias do litoral.\n" +
                            "Época de floração de abril a agosto.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_arrudadapraia_1",
                            "img_guiadecampo_arrudadapraia_2",
                            "img_guiadecampo_arrudadapraia_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Morganheira-das-praias",
                    "Euphorbia paralias",
                    "Dunas",
                    "Flora",
                    "Euphorbiaceae",
                    "Planta vivaz com cerca de 20 a 70cm de altura, ramificada perto da base. Planta monóica (presença dos dois órgãos reprodutores na mesma planta), verde-azulada e carnuda. As folhas são espessas e inteiras.\n" +
                            "Presente em areias marítimas, principalmente em dunas primárias, podendo também estar presente nas dunas embrionárias.\n" +
                            "Época de floração de março a outubro.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_morganheiradaspraias_1",
                            "img_guiadecampo_morganheiradaspraias_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Ononis",
                    "Ononis variegata",
                    "Dunas",
                    "Flora",
                    "Fabaceae",
                    "Planta anual (terófito) podendo atingir os 40 cm. Caules  com pelos e ramificados na base, folhas dentadas e flores amarelas. \n" +
                            "Presente nas zonas interdunares, podendo também estar presentes nas clareiras de pinhal ou mais raramente em dunas primárias.\n" +
                            "Época de floração de março a junho.",
                    "",
                    "",
                    "",
                    "A família Fabaceae é uma família de distribuição cosmopolita  e onde podemos encontrar as leguminosas. Nesta família podemos encontrar várias espécies que estão na nossa alimentação diária, como por exemplo as ervilhas. São facilmente reconhecidas pelo seu fruto em vagem e têm uma grande importância ecológica pela fixação do azoto que é uma fonte de energia para outras espécies.\n" +
                            "Esta espécie tem o estatuto de “quase ameaçada”.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_ononis_1",
                            "img_guiadecampo_ononis_2",
                            "img_guiadecampo_ononis_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Medicago",
                    "Medicago littoralis",
                    "Dunas",
                    "Flora",
                    "Fabaceae",
                    "Planta anual (terófito) herbácea, ramificada junto ao solo com flores amarelas e folhas verdes, serradas na periferia. O fruto é uma vagem em espiral em forma de “donut”.\n" +
                            "A época de floração ocorre de fevereiro a julho.",
                    "",
                    "",
                    "",
                    "A família Fabaceae é uma família de distribuição cosmopolita  e onde podemos encontrar as leguminosas. Nesta família podemos encontrar várias espécies que estão na nossa alimentação diária, como por exemplo as ervilhas. São facilmente reconhecidas pelo seu fruto em vagem e têm uma grande importância ecológica pela fixação do azoto que é uma fonte de energia para outras espécies.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_medicago_1",
                            "img_guiadecampo_medicago_2",
                            "img_guiadecampo_medicago_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Tomilho-das-praias",
                    "Thymus carnosus",
                    "Dunas",
                    "Flora",
                    "Lamiaceae",
                    "Sub-arbusto lenhoso, com tamanho entre 13 a 30cm. Folhas ovadas a elípticas, carnudas, um pouco brilhantes e de cor verde-escura. Flor branca.\n" +
                            "Época de floração de março a setembro.\n" +
                            "Encontra-se no litoral, principalmente na  parte interior da duna primária, podendo ser também encontrado na duna secundária e sob coberto de pinhais.",
                    "",
                    "",
                    "",
                    "Endemismo exclusivo do sudoeste da península ibérica, presente no Alentejo e Algarve. É uma espécie legalmente protegida e pode estar em risco pelo desenvolvimento turístico, pela expansão de plantas exóticas, pela erosão das dunas e a pela subida do nível da água.\n" +
                            "Esta planta tem um odor que nos faz recordar a lavanda",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_tomilhodaspraias_1",
                            "img_guiadecampo_tomilhodaspraias_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    "Um endemismo ibérico, presente no Alentejo e Algarve, com um odor que faz lembrar a lavanda"
            ),
            new EspecieRiaFormosa(
                    "Hypecoum",
                    "Hypecoum procumbens",
                    "Dunas",
                    "Flora",
                    "Papaveraceae",
                    "Planta anual, herbácea. Ramos junto ao solo, flor amarela e folhas com margens lisas e divididas (em que os segmentos formados alcançam o nervo central.\n" +
                            "Época de floração de fevereiro a maio.\n" +
                            "Presente na zona interdunar.",
                    "",
                    "",
                    "",
                    "Esta espécie, em Portugal, só existe na Ria Formosa e está classificada como “Criticamente em Perigo”",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_hypecoum_1",
                            "img_guiadecampo_hypecoum_2",
                            "img_guiadecampo_hypecoum_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    "Em Portugal só existe na Ria Formosa, encontrando-se criticamente em perigo"
            ),
            new EspecieRiaFormosa(
                    "Feno-das-areias",
                    "Elymus farctus",
                    "Dunas",
                    "Flora",
                    "Poaceae",
                    "Planta vivaz com caules delgados rígidos e sem a presença de pelos e podem atingir cerca de 60cm de altura.\n" +
                            "As folhas são rígidas e podem ser enroladas ou planas e com a parte superior relativamente lisa e brilhante. A parte inferior da folha apresenta fendas longitudinais ao longo da folha.\n" +
                            "Encontrado nas dunas primárias, podendo estar também presente nas dunas embrionárias.\n" +
                            "A época de floração ocorre entre junho e julho.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_fenodasareias_1",
                            "img_guiadecampo_fenodasareias_2",
                            "img_guiadecampo_fenodasareias_3"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Estorno",
                    "Ammophila arenaria",
                    "Dunas",
                    "Flora",
                    "Poaceae",
                    "Planta vivaz formada por tufos com 50 a 150cm de altura. As folhas são enroladas, rígidas e com pelos (fracos mas densos) no interior . As folhas no exterior não apresentam pêlos, são lisas e brilhantes\n" +
                            "A época de floração ocorre de abril a junho.\n" +
                            "Presentes nas dunas e areias do litoral, dominância na duna primária.",
                    "",
                    "",
                    "",
                    "O estorno é uma planta pioneira na fixação das dunas, possui uma capacidade de regeneração e crescimento elevada. As areias, transportadas pelo vento, ficam retidas nos seus tufos.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_estorno_1"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Morrião-azul",
                    "Anagallis monelli",
                    "Dunas",
                    "Flora",
                    "Primulaceae",
                    "Planta vivaz, herbácea ou sub-arbustiva. Pode chegar aos 50/60cm de altura. Caules deitados sobre o solo, ramificados e lenhosos na base. As folhas, geralmente, são opostas umas das outras. As flores apresentam uma cor roxa ou azulada.\n" +
                            "Presente na zona limite para o sub-bosque/mata na duna secundária. Também pode estar presente em bermas de caminhos e em sítios secos e pedregosos.\n" +
                            "Época de floração de março a julho.",
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_morriaoazul_1",
                            "img_guiadecampo_morriaoazul_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            ),
            new EspecieRiaFormosa(
                    "Chorão-das-praias",
                    "Carpobrotus edulis",
                    "Dunas",
                    "Flora",
                    "Aizoaceae",
                    "Planta perene, herbácea e de folhas carnudas. Os caules rastejantes podem atingir os 2m de comprimento. As folhas têm uma cor verde-vivo mas podem apresentar também tons avermelhados. As flores podem apresentar várias cores, como o amarela, roxo.",
                    "",
                    "",
                    "",
                    "Espécie invasora tendo sido introduzida para o uso ornamental e para a fixação de dunas e taludes. \n" +
                            "De crescimento rápido, forma extensos tapetes que impedem a instalação e permanência das espécies de vegetação nativas.",
                    new ArrayList<String>(Arrays.asList(
                            "img_guiadecampo_choraodaspraias_1",
                            "img_guiadecampo_choraodaspraias_2"
                    )),
                    new ArrayList<String>(Arrays.asList()),
                    new ArrayList<String>(Arrays.asList()),
                    "",
                    ""
            )
    );
}
