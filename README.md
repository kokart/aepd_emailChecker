# aepd_tools



## Información

Este es un proyecto muy simple que comprueba si un email es válido sintácticamente.
Si se tiene Java 1.8, se tiene que utilizar la versión emailChecker18.jar
Si se tiene Java 1.11 o superior, se tiene que utilizar la versión emailCheckerv2.jar

Una vez se ejecuta el programa Jar;
1. Seleccionar el fichero csv que contiene los emails que se quieren verificar
2. Indicar si queremos que los correos de la agencia (Aepd y agpd se incluyan o no)
3. Pulsar en Generar Fichero

Se generan dos archivos:
- emailsNoValidos.csv: Fichero csv con los emails que no son válidos.
- emailsValidos.csv: Fichero csv con los emails válidos. Este fichero no contiene ningún correo de la AEPD.

## Cargar Proyecto
Es un proyecto simple de Java. Se tiene que  crear un proyecto Java en cualquier IDE y cargar el código fuente. Además, hay que añadir al PATH del proyecto las dos librerías utilizadas para validar el email:
- commons-validator-1.7
- jmail-1.5.0
Cualquier duda, se puede escribir a asoto@aepd.es

Gracias,

