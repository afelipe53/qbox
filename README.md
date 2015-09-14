#INTRODUÇÃO
--------------------------------------------------------------------------

QBox é uma ferramenta para armazenamento de arquivos na web.
Desenvolvido usando Java/Spring MVC e IoC container/Hibernate/MySQL no backend e JSP/Semantic-UI no frontend


#INSTALAÇÃO
--------------------------------------------------------------------------

1. Alterar o persistence.xml do projeto com os dados do banco MySQL que será utilizado;
2. Realizar o build do projeto utilizando maven a partir da pasta raíz (mvn clean install);
3. Copiar o .war gerado no target e realizar o deploy em um servidor jboss wildfly;
4. Acessar "host do servidor + porta"/app/home;
5. Ao acessar pela primeira vez ele vai pedir para indicar qual a pasta no servidor onde ficarão os arquivos;
6. Criar um novo usuário e começar a usar :).

#EM BREVE
--------------------------------------------------------------------------
1. Funcionalidade "Renomear Pasta";
2. Links (gera link da pasta ou do arquivo para compartilhar publicamente);
3. Pesquisar (pesquisa em pastas e arquivos);
4. Perfil (alterar os dados do usuário);
5. Gravar log de ações do usuário no sistema;
6. Download de pastas (em formato zip).

#IMAGENS
--------------------------------------------------------------------------

![Novo usuario](https://cloud.githubusercontent.com/assets/5489533/9751302/6799354a-5679-11e5-9807-2ccad9697de3.png)


![Login](https://cloud.githubusercontent.com/assets/5489533/9751305/72c8e550-5679-11e5-988e-dbf336ee2ee8.png)


![Home](https://cloud.githubusercontent.com/assets/5489533/9751289/2da5cef2-5679-11e5-84ef-daf09a50c901.png)


![Nova pasta](https://cloud.githubusercontent.com/assets/5489533/9751316/81750ff2-5679-11e5-8678-4dd78a2fe42d.png)


![Deletar arquivo](https://cloud.githubusercontent.com/assets/5489533/9751320/8967f558-5679-11e5-882e-76d8a357c625.png)
