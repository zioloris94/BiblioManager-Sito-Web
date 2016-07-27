-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Lug 03, 2016 alle 15:26
-- Versione del server: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `biblio`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `consiglia`
--

CREATE TABLE IF NOT EXISTS `consiglia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` int(11) NOT NULL,
  `pubblicazione` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `utente` (`utente`),
  KEY `pubblicazione` (`pubblicazione`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dump dei dati per la tabella `consiglia`
--

INSERT INTO `consiglia` (`id`, `utente`, `pubblicazione`) VALUES
(17, 10, 38),
(18, 13, 31),
(20, 10, 37),
(21, 10, 39),
(22, 13, 40),
(23, 10, 40),
(24, 13, 39),
(25, 10, 41),
(26, 10, 42),
(27, 13, 37);

-- --------------------------------------------------------

--
-- Struttura della tabella `galleria`
--

CREATE TABLE IF NOT EXISTS `galleria` (
  `chiave` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) NOT NULL,
  `id_ristampa` int(11) NOT NULL,
  PRIMARY KEY (`chiave`),
  KEY `id_ristampa` (`id_ristampa`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dump dei dati per la tabella `galleria`
--

INSERT INTO `galleria` (`chiave`, `img`, `id_ristampa`) VALUES
(2, 'template/upload/aaa.jpg', 31),
(3, 'template/upload/aaa.jpg', 31);

-- --------------------------------------------------------

--
-- Struttura della tabella `gruppi`
--

CREATE TABLE IF NOT EXISTS `gruppi` (
  `id` int(11) NOT NULL,
  `Nome` varchar(50) DEFAULT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `gruppi`
--

INSERT INTO `gruppi` (`id`, `Nome`, `descrizione`) VALUES
(1, 'attivo', 'modificare e consultare il catalogo\npromuovere un utente passivo a attivo'),
(2, 'passivo', 'consultare il catalogo');

-- --------------------------------------------------------

--
-- Struttura della tabella `modifica`
--

CREATE TABLE IF NOT EXISTS `modifica` (
  `Timestamp` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `Descrizione` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `parolechiave`
--

CREATE TABLE IF NOT EXISTS `parolechiave` (
  `id` int(11) NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `pubblicazioni`
--

CREATE TABLE IF NOT EXISTS `pubblicazioni` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titolo` varchar(255) NOT NULL,
  `autore` varchar(255) NOT NULL,
  `descrizione` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dump dei dati per la tabella `pubblicazioni`
--

INSERT INTO `pubblicazioni` (`id`, `titolo`, `autore`, `descrizione`) VALUES
(5, 'Stefano', 'cortellessa', 'fdsdce'),
(12, 'Gomorra', 'Roberto Saviano', 'Nell''aprile 2006 il mondo editoriale italiano Ã¨ stato sconvolto da un bestseller clamoroso e inaspettato, trasformatosi in poco tempo in un terremoto culturale, sociale e civile: Gomorra. Un libro anomalo in cui Roberto Savia'),
(13, 'Divina Commedia', 'Dante Alighieri', 'cammin di nostra vita'),
(14, '50 sfumature di grigio', 'Oscar Wild', 'Quando Anastasia Steele, graziosa e ingenua studentessa americana di ventun anni incontra Christian Grey, giovane imprenditore miliardario, si accorge di essere attratta irresistibilmente da quest''uomo bellissimo e misterioso. Convinta perÃ² che il loro incontro non avrÃ  mai un futuro, prova in tutti i modi a smettere di pensarci, fino al giorno in cui Grey non compare improvvisamente nel negozio dove lei lavora e la invita a uscire con lui. Anastasia capisce di volere quest''uomo a tutti i costi. Anche lui Ã¨ incapace di resisterle e deve ammettere con se stesso di desiderarla, ma alle sue condizioni. Travolta dalla passione, presto Anastasia scoprirÃ  che Grey Ã¨ un uomo tormentato dai suoi demoni e consumato dall''ossessivo bisogno di controllo, ma soprattutto ha gusti erotici decisamente singolari e predilige pratiche sessuali insospettabili... Nello scoprire l''animo enigmatico di Grey, Ana conoscerÃ  per la prima volta i suoi piÃ¹ segreti desideri. Tensione erotica travolgente, sensazioni forti, ma anche amore romantico, sono gli ingredienti che E. L. James ha saputo amalgamare osando scoprire il lato oscuro della passione, senza porsi alcun tabÃ¹.\r\nIl successo senza precedenti della trilogia Cinquanta sfumature, di cui questo Ã¨ il primo volume, Ã¨ iniziato grazie al passaparola delle donne che ne hanno fatto nel mondo un vero e proprio cult. Come un ciclone inarrestabile, la passione proibita di Anastasia e Christian ha conquistato le lettrici prima attraverso la diffusione in e-book, poi in edizione tascabile, ponendosi al primo posto in tutte le classifiche del mondo.'),
(15, 'sdf', 'sdf', 'sfd'),
(16, 'asd', 'asd', 'asdasd');

-- --------------------------------------------------------

--
-- Struttura della tabella `recensioni`
--

CREATE TABLE IF NOT EXISTS `recensioni` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `testo` text NOT NULL,
  `titolo` varchar(255) NOT NULL,
  `id_lib` int(11) NOT NULL,
  `utente` varchar(255) NOT NULL,
  `approvato` tinyint(1) NOT NULL,
  `utapprovante` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=62 ;

--
-- Dump dei dati per la tabella `recensioni`
--

INSERT INTO `recensioni` (`id`, `testo`, `titolo`, `id_lib`, `utente`, `approvato`, `utapprovante`) VALUES
(2, 'testo2', 'titolo2', 2, 'gian', 1, ''),
(3, 'speriamo riesce', 'ciao stefano', 2, 'ste', 1, ''),
(5, 'testo PROVA', 'titolo PROVA', 1, 'Stefano', 1, ''),
(7, 'sdfsd', 'fsdf', 22, 'Stefano', 0, ''),
(9, 'asd', 'sda', 5, 'Luca', 1, 'luca@luca.it'),
(10, 'weweeee', 'ds', 5, 'Stefano', 1, 'ste@ste.it'),
(15, 'sdfsdf', 'sdfsd', 6, 'Stefano', 1, 'ste@ste.it'),
(19, 'ciÃ ', 'ciao bello', 18, 'Stefano', 1, 'ste@ste.it'),
(21, 'dsfsd', 'sdaf', 18, 'Stefano', 0, ''),
(24, 'asdas', 'saasd', 2, 'Stefano', 1, 'ste@ste.it'),
(25, 'ciaooooooooooooooooooo', 'dsfsdfdsf', 2, 'Stefano', 1, 'Stefano'),
(26, 'dskvbkjsdbvkjsdbvkj', 'dscbjkj', 2, 'Stefano', 1, 'ste@ste.it'),
(27, 'vm,dnv,mdfvndvm,', 'cxmvncxm,', 2, 'Stefano', 1, 'ste@ste.it'),
(28, 'sdfsdfsdf', 'DSfdsf', 3, 'Luca', 1, 'luca@luca.it'),
(32, 'dsfsdfsdf', 'sadfdsf', 26, 'Luca', 1, 'luca@luca.it'),
(33, 'Algheto', 'Dante ', 38, 'Stefano', 1, 'ste@ste.it'),
(34, 'ciaoooo', 'ciao', 33, 'Stefano', 0, ''),
(35, 'ciaa', 'ciaooo', 32, 'Stefano', 1, 'ste@ste.it'),
(38, 'ciaooo', 'caiooo', 32, 'Luca', 0, ''),
(39, 'ds', 'ds', 32, 'Luca', 0, ''),
(40, 's', 'sd', 32, 'Luca', 0, ''),
(41, 'd', 'd', 32, 'Luca', 0, ''),
(42, 'ddd', 'DSf', 32, 'Luca', 0, ''),
(43, 'dd', 'Dd', 32, 'Luca', 0, ''),
(44, 'd', 'd', 32, 'Luca', 0, ''),
(45, 'sdfdsf', 'dsfdf', 30, 'Luca', 0, ''),
(47, 'wegfasgasgfsad', 'feasf', 40, 'Stefano', 1, 'ste@ste.it'),
(48, 'safdasf', 'sdfa', 40, 'Stefano', 0, ''),
(49, 'dd', 'dd', 31, 'Stefano', 1, 'ste@ste.it'),
(50, 'sds', 'DSd', 37, 'Stefano', 1, 'ste@ste.it'),
(51, 'ddd', 'Dd', 31, 'Stefano', 1, 'ste@ste.it'),
(54, 'ddd', 'dd', 0, 'Stefano', 0, ''),
(55, 'ddd', 'dd', 0, 'Stefano', 0, ''),
(59, 'Testo', 'Titolo', 37, 'Stefano', 1, 'ste@ste.it'),
(61, 'testo', 'titolo', 39, 'Stefano', 1, 'ste@ste.it');

-- --------------------------------------------------------

--
-- Struttura della tabella `ristampe`
--

CREATE TABLE IF NOT EXISTS `ristampe` (
  `identificatore` int(11) NOT NULL AUTO_INCREMENT,
  `id_pubblicazioni` int(11) NOT NULL,
  `inserita` varchar(255) NOT NULL,
  `modificata` varchar(255) NOT NULL,
  `Consigliato` int(11) NOT NULL,
  `Editore` varchar(50) DEFAULT NULL,
  `Indice` varchar(255) DEFAULT NULL,
  `Uri` text,
  `ISBN` varchar(255) DEFAULT NULL,
  `Lingua` varchar(50) DEFAULT NULL,
  `NumeroPagina` int(11) DEFAULT NULL,
  `Data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `path` varchar(255) NOT NULL,
  `edizione` int(11) NOT NULL,
  PRIMARY KEY (`identificatore`),
  KEY `id_pubblicazioni` (`id_pubblicazioni`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

--
-- Dump dei dati per la tabella `ristampe`
--

INSERT INTO `ristampe` (`identificatore`, `id_pubblicazioni`, `inserita`, `modificata`, `Consigliato`, `Editore`, `Indice`, `Uri`, `ISBN`, `Lingua`, `NumeroPagina`, `Data`, `path`, `edizione`) VALUES
(31, 5, 'ste@ste.it', 'luca@luca.it', 4, 'LucaFerrari', 'fvht', 'ftytfy', 'ytfyt', 'ytfytf', 45, '2016-06-23 13:29:15', 'template/upload/aaa.jpg', 5),
(37, 12, 'ste@ste.it', 'ste@ste.it', 10, 'Mondadori', '1inizio, 2la guerra, 3oh ciro, 4 si sphara', 'https://www.amazon.it/Gomorra-Viaggio-nellimpero-economico-dominio/dp/8804664231', '8804664231', 'Italiano', 4, '2016-06-23 14:17:20', 'template/upload/41HfKdK-u2L._SX324_BO1,204,203,200_.jpg', 2),
(38, 13, 'luca@luca.it', 'ste@ste.it', 1, 'Dante Algher', 'dd', 'uu', 'ii', 'italiana', 300, '2016-06-23 15:09:21', 'template/upload/sfondo_paesaggio_bianco&nero.jpg', 2),
(39, 12, 'ss', 'ss', 6, 'Ss', 'sss', 'sss', 'ss', 'sss', 33, '2016-06-30 10:52:09', 'ss', 2),
(40, 14, 'ste@ste.it', 'ste@ste.it', 2, 'pippo', '43', 'https://www.amazon.it/Cinquanta-sfumature-di-Grigio-Omnibus-ebook/dp/B007U23O9U/ref=sr_1_1?s=books&ie=UTF8&qid=1467305936&sr=1-1&keywords=50+sfumature+di+grigio', '9877987', 'nknlku', 67, '2016-06-30 14:02:27', 'template/upload/PSV1423996340PS54e075b4c286d.jpg', 67),
(41, 14, 'ste@ste.it', '', 1, 'IOOOOOOOOOOOO', 'jhj', 'jh', 'jjh', 'jhj', 2, '2016-07-03 10:57:16', 'template/upload/aaa.jpg', 2),
(42, 14, 'ste@ste.it', '', 1, 'ddd', 'ddd', 'jnhjb', 'hbkbk', 'jkbkjb', 22, '2016-07-03 10:59:09', '', 22),
(43, 16, 'ste@ste.it', '', 0, 'kghjf', 'jgfjgf', 'jhjh', 'jhfjfh', 'jhfjhf', 5656, '2016-07-03 11:27:37', '', 565),
(44, 13, 'ste@ste.it', '', 0, 'fdsfd', 'hjvfjf', 'chgch', 'gcgh', 'chg', 5656, '2016-07-03 11:27:54', '', 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE IF NOT EXISTS `utenti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `gruppo` int(11) NOT NULL,
  `modifiche` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`email`),
  KEY `gruppo` (`gruppo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id`, `nome`, `cognome`, `email`, `password`, `gruppo`, `modifiche`) VALUES
(10, 'Stefano', 'Cortellessa', 'ste@ste.it', '5b9115df05a2a467841772eccc969822d884c9e71841050fe9e893cce7d11b', 1, 30),
(11, 'Loris', 'Fratarcangeli', 'loris@loris.it', '73b8a7f3cffd4fa177259cd9993763b61ff45cc2c5beb825ce05b71de1283', 1, 0),
(13, 'Luca', 'Ferrari', 'luca@luca.it', 'd7f4779f689414789eeff231703429c7f88a1021775906460edbf38589d90', 2, 14),
(26, 'Giovanni', 'Cortellessa', 'gio@cort.it', '192c33caac3d89ed647f6dc54419161c2bbf4b57d12bb8c546e41d6448597571', 2, 0);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `consiglia`
--
ALTER TABLE `consiglia`
  ADD CONSTRAINT `fk_pubblicazioni_utenti` FOREIGN KEY (`pubblicazione`) REFERENCES `ristampe` (`identificatore`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_utenti_consiglia` FOREIGN KEY (`utente`) REFERENCES `utenti` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `galleria`
--
ALTER TABLE `galleria`
  ADD CONSTRAINT `fk_r_i` FOREIGN KEY (`id_ristampa`) REFERENCES `ristampe` (`identificatore`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `ristampe`
--
ALTER TABLE `ristampe`
  ADD CONSTRAINT `ttfhy` FOREIGN KEY (`id_pubblicazioni`) REFERENCES `pubblicazioni` (`id`);

--
-- Limiti per la tabella `utenti`
--
ALTER TABLE `utenti`
  ADD CONSTRAINT `fk_utenti_gruppi` FOREIGN KEY (`gruppo`) REFERENCES `gruppi` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
