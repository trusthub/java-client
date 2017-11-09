# Trusthub API Java client

Cliente Java para integração com a API Trusthub

Esse cliente permite o envio de notas fiscais e consulta de status de notas fiscais.

As URLs são:

**Envio de notas**
https://api.hom.trusthub.com.br/invoices/v1/ 


**Consulta de uma nota fiscal pela chave **
https://api.hom.trusthub.com.br/invoices/v1/999999

**Lista pagina de notas fiscais **
https://api.hom.trusthub.com.br/invoices/v1/1/1?client_id=11212 



O projeto contém 3 classes de teste para integração com a API Trusthub:

- InvoiceConsult.java (consulta de uma nota fiscal apenas pela chave)
- InvoiceInsert.java (envio de uma ou mais notas fiscais)
- InvoiceList (consulta paginada de múltiplas notas)
