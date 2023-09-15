import React, { useState } from 'react';
import '../styles/finance.css';

const FinancePage = () => {
  const [loanAmount, setLoanAmount] = useState(10000);
  const [interestRate, setInterestRate] = useState(5);
  const [loanTenure, setLoanTenure] = useState(12);
  const [emi, setEmi] = useState(0);

  const calculateEmi = () => {
    const principal = parseFloat(loanAmount);
    const rate = parseFloat(interestRate) / 100 / 12;
    const tenure = parseFloat(loanTenure);

    const emiValue = (principal * rate * Math.pow(1 + rate, tenure)) / (Math.pow(1 + rate, tenure) - 1);
    setEmi(emiValue.toFixed(2));
  };

  return (
    <div className="finance-container">
      <h1 className='f_h1'>EMI Calculator</h1>
      <div className="input-container">
        <label className="custom-label">Loan Amount</label>
        <input
          type="range"
          min="1000"
          max="100000"
          step="1000"
          value={loanAmount}
          onChange={(e) => setLoanAmount(e.target.value)}
          className="custom-slider"
        />
        <span className="custom-value">{loanAmount}</span>
      </div>
      <div className="input-container">
        <label className="custom-label">Interest Rate (%)</label>
        <input
          type="range"
          min="1"
          max="20"
          step="0.1"
          value={interestRate}
          onChange={(e) => setInterestRate(e.target.value)}
          className="custom-slider"
        />
        <span className="custom-value">{interestRate}</span>
      </div>
      <div className="input-container">
        <label className="custom-label">Loan Tenure (months)</label>
        <input
          type="range"
          min="1"
          max="360"
          step="1"
          value={loanTenure}
          onChange={(e) => setLoanTenure(e.target.value)}
          className="custom-slider"
        />
        <span className="custom-value">{loanTenure}</span>
      </div>
      <button onClick={calculateEmi} className="custom-button">
        Calculate EMI
      </button>
      <div className="result">
        {emi > 0 && <p>Your EMI: ₹ {emi}</p>}
      </div>
    </div>
  );
};

export default FinancePage;