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
      <v-col>Produtos</v-col>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Pesquisar"
        single-line
        hide-details
      />
      <v-spacer />
      <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark v-on="on">Adicionar Produto</v-btn>
        </template>
        <form @submit.prevent="createProduct" :disabled="!isFormValid">
          <v-card :loading="loadingCreate">
            <v-card-title>
              <span class="headline">{{ title }}</span>
            </v-card-title>
            <v-card-text>
              <input type="hidden" :value="editing" id="dialogType" />
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.code"
                      label="Código*"
                      type="number"
                      min="1"
                      step="1"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.value"
                      label="Valor*"
                      suffix="€"
                      type="number"
                      min="0"
                      step=".01"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.description"
                      label="Descrição*"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-autocomplete
                      v-model="create.type"
                      :items="['Artigo', 'Seguro', 'Graduação', 'Inscrição', 'Quota', 'Aula', 'Estágio', 'Outro']"
                      label="Tipo*"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                </v-row>
              </v-container>
              <small>*indica campos de preenchimento obrigatório</small>
            </v-card-text>

            <v-card-actions>
              <v-spacer />
              <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
              <v-btn
                color="blue darken-1"
                :loading="loadingCreate"
                text
                type="submit"
                @click.prevent="createProduct"
                :disabled="!isFormValid"
              >Save</v-btn>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>
      <v-spacer />
      <v-btn @click="getProducts" icon :loading="loading">
        <v-icon>refresh</v-icon>
      </v-btn>
    </v-card-title>
    <v-data-table
      :search="search"
      :headers="headers"
      :items="products"
      :items-per-page="5"
      class="elevation-1"
      item-key="code"
      loading-text="A carregar ..."
      :loading="loading"
    >
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="viewProduct(item)">edit</v-icon>
        <v-icon small @click="deleteProduct(item)">delete</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      headers: [
        { text: "Código", value: "code" },
        { text: "Descrição", value: "description" },
        { text: "Tipo", value: "type" },
        { text: "Valor (€)", value: "value" },
        { text: "Ações", value: "action" }
      ],
      products: [],
      loading: true,
      loadingCreate: false,
      search: "",
      dialog: false,
      title: "Criar Produto",
      editing: false,
      alertSuccess: false,
      alertError: false,
      create: {
        code: "",
        description: "",
        type: "",
        value: ""
      },
      rules: {
        required: value => !!value || "Preenchimento Obrigatório"
      }
    };
  },
  methods: {
    createProduct() {
      this.loadingCreate = true;

      if (this.editing) {
        this.updateProduct();

        return;
      }

      let type = "";

      switch (this.create.type) {
        case "Artigo":
          type = "ARTIGO";
          break;
        case "Seguro":
          type = "SEGURO";
          break;
        case "Graduação":
          type = "GRADUACAO";
          break;
        case "Inscrição":
          type = "INSCRICAO";
          break;
        case "Quota":
          type = "QUOTA";
          break;
        case "Aula":
          type = "AULA";
          break;
        case "Estágio":
          type = "ESTAGIO";
          break;
        case "Outro":
          type = "OUTRO";
          break;
      }

      this.$axios
        .post("/api/products", {
          code: parseInt(this.create.code),
          value: parseFloat(this.create.value),
          description: this.create.description,
          type: type
        })
        .then(response => {
          if (response.status != 201) {
            this.loadingCreate = false;
            this.closeDialog();
            this.alertError = true;
          }

          this.loadingCreate = false;
          this.closeDialog();
          this.alertSuccess = true;
          this.getProducts();
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    updateProduct() {
      let type = "";

      switch (this.create.type) {
        case "Artigo":
          type = "ARTIGO";
          break;
        case "Seguro":
          type = "SEGURO";
          break;
        case "Graduação":
          type = "GRADUACAO";
          break;
        case "Inscrição":
          type = "INSCRICAO";
          break;
        case "Quota":
          type = "QUOTA";
          break;
        case "Aula":
          type = "AULA";
          break;
        case "Estágio":
          type = "ESTAGIO";
          break;
        case "Outro":
          type = "OUTRO";
          break;
      }

      this.$axios
        .put("/api/products/" + this.create.code, {
          code: parseInt(this.create.code),
          value: parseFloat(this.create.value),
          description: this.create.description,
          type: type
        })
        .then(() => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertSuccess = true;
          this.getProducts();
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    deleteProduct(item) {
      this.$axios
        .delete("/api/products/" + item.code + "/delete")
        .then(response => {
          this.alertSuccess = true;
          this.getProducts();
        })
        .catch(er => {
          this.alertError = true;
          console.log(er);
        });
    },
    viewProduct(item) {
      this.title = `Produto ${item.code}`;
      this.editing = true;
      this.dialog = true;

      this.getProduct(item.code);
    },
    getProduct(code) {
      this.loadingCreate = true;

      this.$axios
        .get("/api/products/" + code + "/details")
        .then(response => {
          let data = response.data;

          this.create.code = data.code;
          this.create.description = data.description;
          this.create.value = data.value;

          switch (data.type) {
            case "ARTIGO":
              this.create.type = "Artigo";
              break;
            case "SEGURO":
              this.create.type = "Seguro";
              break;
            case "GRADUACAO":
              this.create.type = "Graduação";
              break;
            case "INSCRICAO":
              this.create.type = "Inscrição";
              break;
            case "QUOTA":
              this.create.type = "Quota";
              break;
            case "AULA":
              this.create.type = "Aula";
              break;
            case "ESTAGIO":
              this.create.type = "Estágio";
              break;
            case "OUTRO":
              this.create.type = "Outro";
              break;
          }

          this.loadingCreate = false;
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    getProducts() {
      this.loading = true;
      this.products = [];

      this.$axios
        .get("/api/products")
        .then(response => {
          response.data.forEach(p => {
            switch (p.type) {
              case "ARTIGO":
                p.type = "Artigo";
                break;
              case "SEGURO":
                p.type = "Seguro";
                break;
              case "GRADUACAO":
                p.type = "Graduação";
                break;
              case "INSCRICAO":
                p.type = "Inscrição";
                break;
              case "QUOTA":
                p.type = "Quota";
                break;
              case "AULA":
                p.type = "Aula";
                break;
              case "ESTAGIO":
                p.type = "Estágio";
                break;
              case "OUTRO":
                p.type = "Outro";
                break;
            }
          });

          this.products = response.data;
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
      this.title = "Criar Produto";
      this.editing = false;

      this.create = {
        code: "",
        description: "",
        type: "",
        value: ""
      };
    },
    isCodeValid() {
      return this.create.code.length !== 0;
    },
    isDescriptionValid() {
      return this.create.description.length !== 0;
    },
    isTypeValid() {
      if (this.create.type.length === 0) {
        return false;
      }

      switch (this.create.type) {
        case "Artigo":
          break;
        case "Seguro":
          break;
        case "Graduação":
          break;
        case "Inscrição":
          break;
        case "Quota":
          break;
        case "Aula":
          break;
        case "Estágio":
          break;
        case "Outro":
          break;
        default:
          return false;
      }

      return true;
    },
    isValueValid() {
      return this.create.value.length !== 0;
    }
  },
  computed: {
    isFormValid() {
      if (!this.isCodeValid()) {
        //console.log("code error");
        return false;
      }

      if (!this.isDescriptionValid()) {
        //console.log("description error");
        return false;
      }

      if (!this.isTypeValid()) {
        //console.log("type error");
        return false;
      }

      if (!this.isValueValid()) {
        //console.log("value error");
        return false;
      }

      //console.log("valid form");
      return true;
    }
  },
  mounted() {
    this.getProducts();
  }
};
</script>
