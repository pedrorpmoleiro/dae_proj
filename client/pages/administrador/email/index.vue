<template>
  <v-container>
    <v-container>
      <v-alert
        dense
        text
        v-model="alertSuccess"
        close-text="Fechar"
        dismissible
        type="success"
      >Sucesso</v-alert>
      <v-alert
        dense
        text
        v-model="alertError"
        close-text="Fechar"
        dismissible
        type="error"
      >Ocorreu um erro. Tente novamente mais tarde.</v-alert>
    </v-container>
    <v-card-title>
      <v-col>Socios</v-col>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Pesquisar"
        single-line
        hide-details
      />
      <v-spacer />
      <v-dialog v-model="dialog" persistent max-width="600px">
        <form @submit.prevent="sendEmail" :disabled="!isFormValid">
          <v-card :loading="loadingCreate">
            <v-card-title>
              <span class="headline">{{ title }}</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.subject"
                      label="Assunto"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-textarea
                      counter
                      auto-grow
                      v-model="create.message"
                      label="Mensagem"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn color="blue darken-1" text @click="closeDialog">Cancelar</v-btn>
              <v-btn
                color="blue darken-1"
                :loading="loadingCreate"
                text
                type="submit"
                @click.prevent="sendEmail"
                :disabled="!isFormValid"
              >Enviar</v-btn>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>
      <v-btn @click="getSocios" icon :loading="loading">
        <v-icon>refresh</v-icon>
      </v-btn>
    </v-card-title>
    <v-data-table
      :search="search"
      :headers="headers"
      :items="socios"
      :items-per-page="5"
      class="elevation-1"
      item-key="username"
      loading-text="A carregar ..."
      :loading="loading"
    >
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="viewDialog(item)">mail</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      headers: [
        { text: "ID", value: "idSocio" },
        { text: "Username", value: "username" },
        { text: "Nome", value: "name" },
        { text: "Tipo", value: "tipo" },
        { text: "E-Mail", value: "email" },
        { text: "Ações", value: "action" }
      ],
      socios: [],
      loading: true,
      loadingCreate: false,
      search: "",
      dialog: false,
      title: "Enviar Email",
      alertSuccess: false,
      alertError: false,
      create: {
        username: "",
        message: "",
        subject: ""
      },
      rules: {
        required: value => !!value || "Preenchimento Obrigatório"
      }
    };
  },
  methods: {
    getSocios() {
      this.loading = true;
      this.socios = [];

      this.$axios
        .get("/api/users")
        .then(response => {
          this.socios = response.data;
          this.loading = false;
        })
        .catch(er => {
          this.loading = false;
          this.alertError = true;
          console.log(er);
        });
    },
    closeDialog() {
      this.dialog = false;

      this.create = {
        username: "",
        message: "",
        subject: ""
      };
    },
    viewDialog(item) {
      this.create.username = item.username;
      this.dialog = true;
    },
    sendEmail() {
      this.loadingCreate = true;

      this.$axios
        .post("/api/email/" + this.create.username, {
          message: this.create.message,
          subject: this.create.subject
        })
        .then(() => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertSuccess = true;
        })
        .catch(er => {
          this.loadingCreate = false;
          this.alertError = false;
          console.log(er);
        });
    },
    isMessageValid() {
      return this.create.message.length !== 0;
    },
    isSubjectValid() {
      return this.create.subject.length !== 0;
    }
  },
  computed: {
    isFormValid() {
      if (!this.isMessageValid()) {
        return false;
      }

      return this.isSubjectValid();
    }
  },
  mounted() {
    this.getSocios();
  }
};
</script>

<style scoped>
</style>
