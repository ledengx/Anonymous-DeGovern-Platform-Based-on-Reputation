package com.web3.degovern.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Replay_protect extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506103e8600060405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506064600060405180807f4f73636172000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555060008060405180807f626f62000000000000000000000000000000000000000000000000000000000081525060030190509081526020016040518091039020819055506201e240600260405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506201e240600260405180807f4f7363617200000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506201e240600260405180807f626f62000000000000000000000000000000000000000000000000000000000081525060030190509081526020016040518091039020819055506000600360405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506001600360405180807f4f7363617200000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506002600360405180807f626f620000000000000000000000000000000000000000000000000000000000815250600301905090815260200160405180910390208190555042600460405180807f416c696365000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555042600460405180807f4f73636172000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555042600460405180807f626f6200000000000000000000000000000000000000000000000000000000008152506003019050908152602001604051809103902081905550610c0b806103536000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806335ee5f871461007257806355b7c6fe146100ef57806364a37f951461016c578063a780003f146101e9578063f978fd6114610339575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610382565b6040518082815260200191505060405180910390f35b3480156100fb57600080fd5b50610156600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506103f6565b6040518082815260200191505060405180910390f35b34801561017857600080fd5b506101d3600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061046b565b6040518082815260200191505060405180910390f35b3480156101f557600080fd5b506102be600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291908035906020019092919080359060200190929190803590602001909291905050506104e0565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102fe5780820151818401526020810190506102e3565b50505050905090810190601f16801561032b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561034557600080fd5b506103686004803603810190808035600019169060200190929190505050610bbf565b604051808215151515815260200191505060405180910390f35b600080826040518082805190602001908083835b6020831015156103bb5780518252602082019150602081019050602083039250610396565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60006003826040518082805190602001908083835b602083101515610430578051825260208201915060208101905060208303925061040b565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60006004826040518082805190602001908083835b6020831015156104a55780518252602082019150602081019050602083039250610480565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60606000808560028a6040518082805190602001908083835b60208310151561051e57805182526020820191506020810190506020830392506104f9565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020541415156105c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f496e76616c6964207369676e617475726500000000000000000000000000000081525060200191505060405180910390fd5b8460038a6040518082805190602001908083835b60208310151561060057805182526020820191506020810190506020830392506105db565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020541415156106a9576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c6964206e6f6e63650000000000000000000000000000000000000081525060200191505060405180910390fd5b8360048a6040518082805190602001908083835b6020831015156106e257805182526020820191506020810190506020830392506106bd565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205414151561078b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f496e76616c69642074696d657374616d7000000000000000000000000000000081525060200191505060405180910390fd5b6000896040518082805190602001908083835b6020831015156107c3578051825260208201915060208101905060208303925061079e565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549150868210156108d8576040805190810160405280600481526020017f4661696c000000000000000000000000000000000000000000000000000000008152506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561089d578082015181840152602081019050610882565b50505050905090810190601f1680156108ca5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b6000886040518082805190602001908083835b60208310151561091057805182526020820191506020810190506020830392506108eb565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050808782011015610a27576040805190810160405280600481526020017f4661696c000000000000000000000000000000000000000000000000000000008152506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b838110156109ec5780820151818401526020810190506109d1565b50505050905090810190601f168015610a195780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b86820360008a6040518082805190602001908083835b602083101515610a625780518252602082019150602081019050602083039250610a3d565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055508681016000896040518082805190602001908083835b602083101515610ad45780518252602082019150602081019050602083039250610aaf565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055504260048a6040518082805190602001908083835b602083101515610b445780518252602082019150602081019050602083039250610b1f565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055506040805190810160405280600781526020017f5375636365737300000000000000000000000000000000000000000000000000815250925050509695505050505050565b60016020528060005260406000206000915054906101000a900460ff16815600a165627a7a7230582001b283dd56d8462c761cf0eb48937f152d35fa0a4439a5824bf5323dc7064c320029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506103e8600060405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506064600060405180807f4f73636172000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555060008060405180807f626f62000000000000000000000000000000000000000000000000000000000081525060030190509081526020016040518091039020819055506201e240600260405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506201e240600260405180807f4f7363617200000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506201e240600260405180807f626f62000000000000000000000000000000000000000000000000000000000081525060030190509081526020016040518091039020819055506000600360405180807f416c69636500000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506001600360405180807f4f7363617200000000000000000000000000000000000000000000000000000081525060050190509081526020016040518091039020819055506002600360405180807f626f620000000000000000000000000000000000000000000000000000000000815250600301905090815260200160405180910390208190555042600460405180807f416c696365000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555042600460405180807f4f73636172000000000000000000000000000000000000000000000000000000815250600501905090815260200160405180910390208190555042600460405180807f626f6200000000000000000000000000000000000000000000000000000000008152506003019050908152602001604051809103902081905550610c0b806103536000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680636cec324b1461007257806394ee2d85146100ef578063a627ef1d1461023f578063cd93c25d146102bc578063fe4f850214610339575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610382565b6040518082815260200191505060405180910390f35b3480156100fb57600080fd5b506101c4600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291908035906020019092919080359060200190929190803590602001909291905050506103f7565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102045780820151818401526020810190506101e9565b50505050905090810190601f1680156102315780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561024b57600080fd5b506102a6600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610ad6565b6040518082815260200191505060405180910390f35b3480156102c857600080fd5b50610323600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610b4b565b6040518082815260200191505060405180910390f35b34801561034557600080fd5b506103686004803603810190808035600019169060200190929190505050610bbf565b604051808215151515815260200191505060405180910390f35b60006004826040518082805190602001908083835b6020831015156103bc5780518252602082019150602081019050602083039250610397565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60606000808560028a6040518082805190602001908083835b6020831015156104355780518252602082019150602081019050602083039250610410565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020541415156104de576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f496e76616c6964207369676e617475726500000000000000000000000000000081525060200191505060405180910390fd5b8460038a6040518082805190602001908083835b60208310151561051757805182526020820191506020810190506020830392506104f2565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020541415156105c0576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c6964206e6f6e63650000000000000000000000000000000000000081525060200191505060405180910390fd5b8360048a6040518082805190602001908083835b6020831015156105f957805182526020820191506020810190506020830392506105d4565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020541415156106a2576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f496e76616c69642074696d657374616d7000000000000000000000000000000081525060200191505060405180910390fd5b6000896040518082805190602001908083835b6020831015156106da57805182526020820191506020810190506020830392506106b5565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549150868210156107ef576040805190810160405280600481526020017f4661696c000000000000000000000000000000000000000000000000000000008152506040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b838110156107b4578082015181840152602081019050610799565b50505050905090810190601f1680156107e15780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b6000886040518082805190602001908083835b6020831015156108275780518252602082019150602081019050602083039250610802565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054905080878201101561093e576040805190810160405280600481526020017f4661696c000000000000000000000000000000000000000000000000000000008152506040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b838110156109035780820151818401526020810190506108e8565b50505050905090810190601f1680156109305780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b86820360008a6040518082805190602001908083835b6020831015156109795780518252602082019150602081019050602083039250610954565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055508681016000896040518082805190602001908083835b6020831015156109eb57805182526020820191506020810190506020830392506109c6565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055504260048a6040518082805190602001908083835b602083101515610a5b5780518252602082019150602081019050602083039250610a36565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055506040805190810160405280600781526020017f5375636365737300000000000000000000000000000000000000000000000000815250925050509695505050505050565b60006003826040518082805190602001908083835b602083101515610b105780518252602082019150602081019050602083039250610aeb565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b600080826040518082805190602001908083835b602083101515610b845780518252602082019150602081019050602083039250610b5f565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60016020528060005260406000206000915054906101000a900460ff16815600a165627a7a723058204709ce756dffc9932d82b29853f34416a2c6a8e567152905acd549cc0a28d66f0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[{\"name\":\"_account\",\"type\":\"string\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_user1\",\"type\":\"string\"}],\"name\":\"getNonce\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_user1\",\"type\":\"string\"}],\"name\":\"getLastTimestamp\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_sender\",\"type\":\"string\"},{\"name\":\"_receiver\",\"type\":\"string\"},{\"name\":\"_amount\",\"type\":\"uint256\"},{\"name\":\"_signature\",\"type\":\"uint256\"},{\"name\":\"_nonce\",\"type\":\"uint256\"},{\"name\":\"_timestamp\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"usedSignatures\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETNONCE = "getNonce";

    public static final String FUNC_GETLASTTIMESTAMP = "getLastTimestamp";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_USEDSIGNATURES = "usedSignatures";

    protected Replay_protect(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public BigInteger balanceOf(String _account) throws ContractException {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger getNonce(String _user1) throws ContractException {
        final Function function = new Function(FUNC_GETNONCE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_user1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger getLastTimestamp(String _user1) throws ContractException {
        final Function function = new Function(FUNC_GETLASTTIMESTAMP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_user1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt transfer(String _sender, String _receiver, BigInteger _amount, BigInteger _signature, BigInteger _nonce, BigInteger _timestamp) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sender), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_receiver), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_amount), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_signature), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_nonce), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] transfer(String _sender, String _receiver, BigInteger _amount, BigInteger _signature, BigInteger _nonce, BigInteger _timestamp, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sender), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_receiver), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_amount), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_signature), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_nonce), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransfer(String _sender, String _receiver, BigInteger _amount, BigInteger _signature, BigInteger _nonce, BigInteger _timestamp) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_sender), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_receiver), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_amount), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_signature), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_nonce), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger> getTransferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue(), 
                (BigInteger) results.get(5).getValue()
                );
    }

    public Tuple1<String> getTransferOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Boolean usedSignatures(byte[] param0) throws ContractException {
        final Function function = new Function(FUNC_USEDSIGNATURES, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static Replay_protect load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Replay_protect(contractAddress, client, credential);
    }

    public static Replay_protect deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Replay_protect.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}