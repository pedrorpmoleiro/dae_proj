<template>
  <v-container>
    <v-card-title>
      <v-col>
        Pagamentos
      </v-col>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      />

      <v-spacer/>
      <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark v-on="on">Adicionar Pagamento</v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">Criar Pagamento</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field label="Código do Pagamento*" type="number" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Preço*" suffix="€" type="number" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Código do Produto*" type="number" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Quantidade*" type="number" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Recibo*" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Nome de usuário do sócio*" required/>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Data e Hora*" type="date" required/>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-autocomplete
                    :items="['Pago', 'Não Pago']"
                    label="Estado"
                  />
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>

          <v-card-actions>
            <v-spacer/>
            <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
            <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-spacer/>
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
        <v-icon
          small
          class="mr-2"
          @click="viewPayment(item)"
        >
          edit
        </v-icon>
        <v-icon
          small
          @click="deletePayment(item)"
        >
          delete
        </v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
  export default {
    data() {
      return {
        headers: [
          {text: 'Código', value: 'code'},
          {text: 'Username do sócio', value: 'socioUsername'},
          {text: 'Código de Produto', value: 'productCode'},
          {text: 'Data', value: 'date'},
          {text: 'Preço (€)', value: 'price'},
          {text: 'Quantidade', value: 'quantity'},
          {text: 'Estado', value: 'status'},
          {text: 'Recibo', value: 'receipt'},
          {text: 'Ações', value: 'action'},
        ],
        payments: [],
        loading: true,
        search: '',
        dialog: false,
      }
    },
    methods: {
      deletePayment(item) {

      },
      viewPayment(item) {

      },
      getPayments() {
        this.loading = true;
        //this.payments = [];

        this.$axios.get('/api/payments').then((response) => {
          response.data.forEach((p) => {
            let date = new Date(0);
            date.setUTCSeconds(p.timestamp);
            p.date = date.toUTCString();

            if (p.status == 'PAID') {
              p.status = "Pago";
            } else {
              p.status = "Não Pago";
            }
          });

          this.payments = response.data;
          this.loading = false;
        });
      }
    },
    mounted() {
      this.getPayments();
    }
  }
</script>

<style scoped>

</style>
