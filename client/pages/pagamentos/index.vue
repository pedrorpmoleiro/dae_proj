<template>
  <v-container>
    <v-card-title>
      <v-col>Pagamentos</v-col>
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
          <v-btn color="primary" dark v-on="on">Adicionar Pagamento</v-btn>
        </template>
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
                    label="Código do Pagamento*"
                    type="number"
                    min="1"
                    step="1"
                    required
                  />
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="create.price"
                    label="Preço*"
                    suffix="€"
                    type="number"
                    min="0"
                    step=".01"
                    required
                  />
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="create.productCode"
                    label="Código do Produto*"
                    type="number"
                    min="1"
                    step="1"
                    required
                  />
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="create.quantity"
                    label="Quantidade*"
                    type="number"
                    min="1"
                    step="1"
                    required
                  />
                </v-col>
                <v-col cols="12">
                  <v-text-field v-model="create.receipt" label="Recibo*" required />
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="create.socioUsername"
                    label="Nome de usuário do sócio*"
                    required
                  />
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="create.date"
                    label="Data e Hora*"
                    type="datetime-local"
                    required
                  />
                </v-col>
                <v-col cols="12" sm="6">
                  <v-autocomplete
                    v-model="create.status"
                    :items="['Pago', 'Não Pago']"
                    label="Estado*"
                  />
                </v-col>
              </v-row>
            </v-container>
            <small>*indica campos de preenchimento obrigatório</small>
          </v-card-text>

          <v-card-actions>
            <v-spacer />
            <v-btn color="blue darken-1" text @click="closeDialog">Close</v-btn>
            <v-btn color="blue darken-1" :loading="loadingCreate" text @click="createPayment">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-spacer />
      <v-btn @click="getPayments" icon :loading="loading">
        <v-icon>refresh</v-icon>
      </v-btn>
    </v-card-title>
    <v-data-table
      :search="search"
      :headers="headers"
      :items="payments"
      :items-per-page="5"
      class="elevation-1"
      item-key="code"
      loading-text="A carregar ..."
      :loading="loading"
    >
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="viewPayment(item)">edit</v-icon>
        <v-icon small @click="deletePayment(item)">delete</v-icon>
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
        { text: "Username do sócio", value: "socioUsername" },
        { text: "Código de Produto", value: "productCode" },
        { text: "Data", value: "date" },
        { text: "Preço (€)", value: "price" },
        { text: "Quantidade", value: "quantity" },
        { text: "Estado", value: "status" },
        { text: "Recibo", value: "receipt" },
        { text: "Ações", value: "action" }
      ],
      payments: [],
      loading: true,
      loadingCreate: false,
      search: "",
      dialog: false,
      title: "Criar Pagamento",
      editing: false,
      create: {
        code: "",
        socioUsername: "",
        productCode: "",
        date: "",
        quantity: "",
        price: "",
        status: "",
        receipt: ""
      },
      rules: {}
    };
  },
  methods: {
    createPayment() {
      if (this.editing) {
        updatePayment();

        return;
      }

      // VALIDATE
    },
    updatePayment() {},
    deletePayment(item) {},
    viewPayment(item) {
      this.title = `Pagamento ${item.code}`;
      this.editing = true;
      this.dialog = true;

      this.getPayment(item.code);
    },
    getPayment(code) {
      this.loadingCreate = true;

      this.$axios.get("/api/payments/" + code + "/details").then(response => {
        let data = response.data;

        this.create.code = data.code;
        this.create.socioUsername = data.socioUsername;
        this.create.productCode = data.productCode;

        let date = new Date(0);
        date.setUTCMilliseconds(data.timestamp);
        this.create.date = date.toLocaleString();

        this.create.quantity = data.quantity;
        this.create.price = data.price;

        if (data.status == "PAID") {
          this.create.status = "Pago";
        } else {
          this.create.status = "Não Pago";
        }

        this.create.receipt = data.receipt;

        this.loadingCreate = false;
      });
    },
    getPayments() {
      this.loading = true;
      //this.payments = [];

      this.$axios.get("/api/payments").then(response => {
        response.data.forEach(p => {
          let date = new Date(0);
          date.setUTCMilliseconds(p.timestamp);
          p.date = date.toLocaleString();

          if (p.status == "PAID") {
            p.status = "Pago";
          } else {
            p.status = "Não Pago";
          }
        });

        this.payments = response.data;
        this.loading = false;
      });
    },
    closeDialog() {
      this.dialog = false;
      this.title = "Criar Pagamento";
      this.editing = false;

      this.create = {
        code: "",
        socioUsername: "",
        productCode: "",
        date: "",
        quantity: "",
        price: "",
        status: "",
        receipt: ""
      };
    }
  },
  mounted() {
    this.getPayments();
  }
};
</script>

<style scoped>
</style>
