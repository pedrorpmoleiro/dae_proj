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
        <form @submit.prevent="createPayment" :disabled="!isFormValid">
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
                      :rules="[rules.required]"
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
                      :rules="[rules.required]"
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
                      :rules="[rules.required]"
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
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.receipt"
                      label="Recibo*"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.socioUsername"
                      label="Nome de usuário do sócio*"
                      required
                      :rules="[rules.required]"
                    />
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="create.date"
                      label="Data e Hora*"
                      type="datetime-local"
                      required
                      :rules="[rules.required, rules.date]"
                    />
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-autocomplete
                      v-model="create.status"
                      :items="['Pago', 'Não Pago', 'Parcial']"
                      label="Estado*"
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
                @click.prevent="createPayment"
                :disabled="!isFormValid"
              >Save</v-btn>
            </v-card-actions>
          </v-card>
        </form>
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
      currentDate: "",
      search: "",
      dialog: false,
      title: "Criar Pagamento",
      editing: false,
      alertSuccess: false,
      alertError: false,
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
      rules: {
        required: value => !!value || "Preenchimento Obrigatório",
        date: value =>
          this.isDateValidValue(value) ||
          "Data não pode ser superior à data e hora atual"
      }
    };
  },
  methods: {
    createPayment() {
      this.loadingCreate = true;

      if (this.editing) {
        this.updatePayment();

        return;
      }

      let status = "";

      switch (this.create.status) {
        case "Pago":
          status = "PAID";
          break;
        case "Não Pago":
          status = "NOT_PAID";
          break;
        case "Parcial":
          status = "PARCIAL";
          break;
      }

      this.$axios
        .post("/api/payments", {
          code: parseInt(this.create.code),
          socioUsername: this.create.socioUsername,
          productCode: parseInt(this.create.productCode),
          date: parseInt(Date.parse(this.create.date)),
          quantity: parseInt(this.create.quantity),
          price: parseFloat(this.create.price),
          status: status,
          receipt: this.create.receipt
        })
        .then(response => {
          if (response.status !== 201) {
            this.loadingCreate = false;
            this.closeDialog();
            this.alertError = true;
          }

          this.loadingCreate = false;
          this.closeDialog();
          this.alertSuccess = true;
          this.getPayments();
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    updatePayment() {
      let status = "";

      switch (this.create.status) {
        case "Pago":
          status = "PAID";
          break;
        case "Não Pago":
          status = "NOT_PAID";
          break;
        case "Parcial":
          status = "PARCIAL";
          break;
      }

      this.$axios
        .put("/api/payments/" + this.create.code, {
          code: parseInt(this.create.code),
          socioUsername: this.create.socioUsername,
          productCode: parseInt(this.create.productCode),
          date: parseInt(Date.parse(this.create.date)),
          quantity: parseInt(this.create.quantity),
          price: parseFloat(this.create.price),
          status: status,
          receipt: this.create.receipt
        })
        .then(() => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertSuccess = true;
          this.getPayments();
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    deletePayment(item) {
      this.$axios
        .delete("/api/payments/" + item.code + "/delete")
        .then(() => {
          this.alertSuccess = true;
          this.getPayments();
        })
        .catch(er => {
          this.alertError = true;
          console.log(er);
        });
    },
    viewPayment(item) {
      this.title = `Pagamento ${item.code}`;
      this.editing = true;
      this.dialog = true;

      this.getPayment(item.code);
    },
    getPayment(code) {
      this.loadingCreate = true;

      this.$axios
        .get("/api/payments/" + code + "/details")
        .then(response => {
          let data = response.data;

          this.create.code = data.code;
          this.create.socioUsername = data.socioUsername;
          this.create.productCode = data.productCode;

          let date = new Date(data.timestamp);
          this.create.date = date.toISOString().split(".")[0];

          this.create.quantity = data.quantity;
          this.create.price = data.price;

          switch (data.status) {
            case "PAID":
              this.create.status = "Pago";
              break;
            case "NOT_PAID":
              this.create.status = "Não Pago";
              break;
            case "PARCIAL":
              this.create.status = "Parcial";
              break;
          }

          this.create.receipt = data.receipt;

          this.loadingCreate = false;
        })
        .catch(er => {
          this.loadingCreate = false;
          this.closeDialog();
          this.alertError = true;
          console.log(er);
        });
    },
    getPayments() {
      this.loading = true;
      this.payments = [];

      this.$axios
        .get("/api/payments")
        .then(response => {
          response.data.forEach(p => {
            let date = new Date(p.timestamp);
            p.date = date.toLocaleString();

            switch (p.status) {
              case "PAID":
                p.status = "Pago";
                break;
              case "NOT_PAID":
                p.status = "Não Pago";
                break;
              case "PARCIAL":
                p.status = "Parcial";
                break;
            }
          });

          this.payments = response.data;
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
    },
    isCodeValid() {
      return this.create.code.length !== 0;
    },
    isPriceValid() {
      return this.create.price.length !== 0;
    },
    isProductCodeValid() {
      return this.create.productCode.length !== 0;
    },
    isQuantityValid() {
      return this.create.quantity.length !== 0;
    },
    isSocioUsernameValid() {
      return this.create.socioUsername.length !== 0;
    },
    isDateValid() {
      return this.isDateValidValue(this.create.date);
    },
    isDateValidValue(value) {
      if (this.create.date.length === 0) {
        return false;
      }

      let dateCreate = Date.parse(value);
      let dateNow = Date.now();

      //console.log("Input timestamp:" + dateCreate);
      //console.log("Current timestamp:" + dateNow);

      return dateCreate <= dateNow;
    },
    isStatusValid() {
      if (this.create.status.length === 0) {
        return false;
      }

      switch (this.create.status) {
        case "Pago":
          break;
        case "Não Pago":
          break;
        case "Parcial":
          break;
        default:
          return false;
      }

      return true;
    },
    isReceiptValid() {
      return this.create.receipt.length !== 0;
    }
  },
  computed: {
    isFormValid() {
      if (!this.isCodeValid()) {
        //console.log("code error");
        return false;
      }

      if (!this.isPriceValid()) {
        //console.log("price error");
        return false;
      }

      if (!this.isProductCodeValid()) {
        //console.log("productCode error");
        return false;
      }

      if (!this.isQuantityValid()) {
        //console.log("quantity error");
        return false;
      }

      if (!this.isSocioUsernameValid()) {
        //console.log("socioUsername error");
        return false;
      }

      if (!this.isDateValid()) {
        //console.log("date error");
        return false;
      }

      if (!this.isStatusValid()) {
        //console.log("status error");
        return false;
      }

      if (!this.isReceiptValid()) {
        //console.log("receipt error");
        return false;
      }

      //console.log("valid form");
      return true;
    }
  },
  mounted() {
    this.getPayments();
  }
};
</script>
