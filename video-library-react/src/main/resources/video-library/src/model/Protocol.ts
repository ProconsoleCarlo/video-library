export enum Protocol {
  JPA = 'jpa',
  JDBC = 'jdbc',
  XLSX = 'xlsx'
}

export module Protocol {
  export const keys = Object.keys(Protocol) as Protocol[];
}